import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/common/product';
import { ActivatedRoute, Route } from '@angular/router';
import { BasketItem } from 'src/app/common/basket-item';
import { BasketService } from 'src/app/services/basket.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {


  products: Product[] = [];
  currentCategoryId: number = 1;
  currentCategoryName: string | null = '';
  searchMode: boolean = false;
  previousCategoryId: number = 1
  previousKeyword: string = "";

  // the Propeties for pagination

  pageNumber: number = 1;
  pageSize: number = 3;
  totalElements: number = 0;




  constructor(private productService: ProductService, private basketService: BasketService, private route: ActivatedRoute) { // injecting product service, ActivatedRoute  and the BasketService

    // 'ActivatedRoute' is the current active route that loaded the component. Useful for accessing route parameters as we need
    // to access a given cateogy id.



  }

  // ngOnInit(): void {
  // }

  ngOnInit() {

    this.route.paramMap.subscribe(() => {
      this.listProducts();

    });
  }

  listProducts() {

    // search mode

    this.searchMode = this.route.snapshot.paramMap.has('keyword'); // to check to see if the route has a parameter for keyword meaning we're searching


    if (this.searchMode) { // if true meaning we're seraching

      this.handleSearchProducts();

    }

    else { // if false meaning just display the products

      this.handleProductList();

    }


  }

  handleSearchProducts() {

    const productSearchKeyword: string = this.route.snapshot.paramMap.get('keyword')?.trim()!;
    //

    // if we have a different keyword than previous then set the 'pageNumber' to 1
    if (this.previousKeyword != productSearchKeyword) {
      this.pageNumber = 1; // reset to origian Page number

    }

    this.previousKeyword = productSearchKeyword;

    console.log(`current keyword: ${productSearchKeyword}, thePageNumber=${this.pageNumber}`);

    // To Search for products using  keyword
    this.productService.searchProductsPaginate(this.pageNumber - 1, this.pageSize, productSearchKeyword).subscribe(this.processResult());

  }


  handleProductList() {

    // To check if "id" parameter is available
    const hasCategoryId: boolean = this.route.snapshot.paramMap.has('id');


    if (hasCategoryId) { // if its true then we need to reed that category id

      // get the "id" param string. Convert String to a number using the "+" symbol
      this.currentCategoryId = +this.route.snapshot.paramMap.get('id')!;

      // "paramMap.get('id')!", '!' is the non-null assertion operator. This tells complier that the object is not null.

      // get the "name" param string CHECK IT
      this.currentCategoryName = this.route.snapshot.paramMap.get('name');

    }

    else {

      // our component will give a default value - if the category ID is not available, then we'll simply default to category
      // ID of 1.
      this.currentCategoryId = 1;
      this.currentCategoryName = 'SMART WATCHES';

    }

    // Need to check if we have a diffeent category than previous

    // NOTE: Angular will resuse a component if it is currently being viewed in the browser then Angular will simply reuse that component, so there is change Angular may not always create a new component every time


    // If we have a different category id than previous then we want to reset the page number back to '1'
    if (this.previousCategoryId != this.currentCategoryId) {

      this.pageNumber = 1;
    }

    this.previousCategoryId = this.currentCategoryId;

    console.log(`current CategpryID: ${this.currentCategoryId}, thePageNumber=${this.pageNumber}`);

    // get the products for the given cateogry
    this.productService.getProductListPaginate(this.pageNumber - 1,
      this.pageSize,
      this.currentCategoryId).subscribe(this.processResult());

    // NOTE: Pagination component in Angular, pages are one based Whereas, in Spring Data REST, the pages zero based

    // Subscribe is going to map data from that JSON response to the actual properteis here for this given class.


  }

  updatePageSize(pageSize: string) {

    this.pageSize = +pageSize;
    this.pageNumber = 1;
    this.listProducts(); // to refresh the page view

  }

  processResult() {
    // taking the JSON response and mapping to the property in this class
    return (data: any) => {
      this.products = data._embedded.products;
      this.pageNumber = data.page.number + 1;
      this.pageSize = data.page.size;
      this.totalElements = data.page.totalElements;
    }

    // the left hand side are the properteis that is declared in this class
    // the right hand side is the data from Spring Data REST JSON 

  }

  addToBasket(itemsForBasket: Product) {
    console.log(`Add to the basket: ${itemsForBasket.name}, ${itemsForBasket.unitPrice}`);

    swal.fire({
      title: 'Are you sure? want to add item',
      icon: "success",
      showCancelButton: true,
      confirmButtonText: 'Yes, add to cart!',
      cancelButtonText: 'No, keep it'
    }).then((result) => {
      if (result.value) {
        const theBasketItem = new BasketItem(itemsForBasket);
        this.basketService.addToBasket(theBasketItem);
        swal.fire(
          ' add To Basket',
          'Item added to Basket',
          'success'
        )
        // For more information about handling dismissals please visit
        // https://sweetalert2.github.io/#handling-dismissals
      } else if (result.dismiss === swal.DismissReason.cancel) {

      }
    })



  }

}
