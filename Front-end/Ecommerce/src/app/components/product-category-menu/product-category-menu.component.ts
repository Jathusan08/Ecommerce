import { Component, OnInit } from '@angular/core';
import { ProductCategory } from 'src/app/common/product-category';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-category-menu',
  templateUrl: './product-category-menu.component.html',
  styleUrls: ['./product-category-menu.component.css']
})
export class ProductCategoryMenuComponent implements OnInit {

  productCategories: ProductCategory[] = []; // this will hold an array of product cateogry items

  constructor(private productService: ProductService) { } // inject the product service

  // ngOnInit(): void {
  // }

  ngOnInit() {
    this.listProductCategories();
  }

  listProductCategories() {
                                      //subscribe - invoke the service
    this.productService.getProductCategories().subscribe(
      data => {
        console.log("Product Categories=" + JSON.stringify(data));
        this.productCategories = data; // assign data to out propery
      }
    );
  }
  
}
