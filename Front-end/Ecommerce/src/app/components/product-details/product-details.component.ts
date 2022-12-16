import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BasketItem } from 'src/app/common/basket-item';
import { Product } from 'src/app/common/product';
import { BasketService } from 'src/app/services/basket.service';
import { ProductService } from 'src/app/services/product.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {

  product?: Product; // the '!' is the non-null assertion operator. It tells TypeScript compiler to suspend strict null and undefined checks for a
  // property.

  constructor(private productService: ProductService, private basketService: BasketService,
    private route: ActivatedRoute) { } // inject product service, BasketService and route depenednecy 

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.sortProductDetails();
    })
  }
  sortProductDetails() {

    // need to get the "id" param String. convert String to a number using the "+"  symbol

    const productId: number = +this.route.snapshot.paramMap.get('id')!;

    this.productService.getProduct(productId).subscribe(
      data => {

        this.product = data;
      }
    )



  }

  addToBasket() {

    console.log(`Add to the basket: ${this.product.name}, ${this.product.unitPrice}`);


    swal.fire({
      title: 'Are you sure? want to add item',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, add to cart!',
      cancelButtonText: 'No, keep it'
    }).then((result) => {
      if (result.value) {

        const theBasketItem = new BasketItem(this.product);

        this.basketService.addToBasket(theBasketItem);
        swal.fire(
          ' addToBasked',
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
