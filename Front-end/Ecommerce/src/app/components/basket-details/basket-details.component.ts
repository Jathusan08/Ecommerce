import { Component, OnInit } from '@angular/core';
import { BasketItem } from 'src/app/common/basket-item';
import { BasketService } from 'src/app/services/basket.service';
import { ProductService } from 'src/app/services/product.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-basket-details',
  templateUrl: './basket-details.component.html',
  styleUrls: ['./basket-details.component.css']
})
export class BasketDetailsComponent implements OnInit {

  basketItems: BasketItem[] = [];
  totalPrice: number = 0.00;
  totalQuantity: number = 0;

  // itemQuanttity = 0;

  constructor(private basketService: BasketService) { } // inject BasketService

  ngOnInit(): void {
    this.listBasketDetails();
  }
  listBasketDetails() {

    // We'll go thorugh and we'll get a handle to the basket items
    this.basketItems = this.basketService.basketItems;


    // Subscribe to the Basket total price
    this.basketService.totalPrice.subscribe(data => this.totalPrice = data);


    // Subscribe to the Basket total Quantity
    this.basketService.totalQuantity.subscribe(data => this.totalQuantity = data);

    // // calculate Basket Total price and total Quantity
    // this.basketService.calculateBasketTotals();


  }

  increaseProductQuantity(theBasketItem: BasketItem) {
    //  console.log(`product Name:${theBasketItem.name}, quantity${theBasketItem.quantity}`);
    this.basketService.addToBasket(theBasketItem);
  }

  decreaseProductQuantity(theBasketItem: BasketItem) {

    this.basketService.decreaseQuantity(theBasketItem);
  }

  removeProduct(theBasketItem: BasketItem) {


    swal.fire({
      title: 'Are you sure? You want to remove the item from the basket',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, remove from the basket cart!',
      cancelButtonText: 'No, keep it'
    }).then((result) => {
      if (result.value) {
        this.basketService.removeProduct(theBasketItem);
        swal.fire(

          'Item removed to Basket',
          'success'
        )
        // For more information about handling dismissals please visit
        // https://sweetalert2.github.io/#handling-dismissals
      } else if (result.dismiss === swal.DismissReason.cancel) {

      }
    })



  }
}
