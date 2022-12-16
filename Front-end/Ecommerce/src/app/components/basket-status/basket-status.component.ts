import { Component, OnInit } from '@angular/core';
import { BasketService } from 'src/app/services/basket.service';

@Component({
  selector: 'app-basket-status',
  templateUrl: './basket-status.component.html',
  styleUrls: ['./basket-status.component.css']
})
export class BasketStatusComponent implements OnInit {

  totalPrice: number =0.00;
  totalQuantity: number = 0;

  constructor(private basketService: BasketService) { } // inject BasketService

  ngOnInit(): void {
    this.updateBasketStatus();

  }
  updateBasketStatus() {
    
    // Subscribe for events on the Basket Service

    // Subsive to the Basket total Price
    this.basketService.totalPrice.subscribe(data => this.totalPrice = data); // when we receive an event from this subsription, we make the assignment to update Basket Total price User Interface

    // Subsive to the Basket total Quantity
    this.basketService.totalQuantity.subscribe(data => this.totalQuantity = data); // when we receive an event from this subsription, we make the assignment to update Basket Total quantity User Interface

  }

}
