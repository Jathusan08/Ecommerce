import { Component, OnInit } from '@angular/core';
import { OrderHistory } from 'src/app/common/order-history';
import { OrderHistoryService } from 'src/app/services/order-history.service';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.css']
})
export class OrderHistoryComponent implements OnInit {

  orderHistoryList: OrderHistory[] = [];

  storage: Storage = localStorage;; // retrieve user email from storage

  // storage: Storage = sessionStorage;

  constructor(private orderHistoryService: OrderHistoryService) { } // inject OrderHistoryService

  ngOnInit(): void {
    this.handleOrderHistory();
  }

  handleOrderHistory() {

    // get the user's email addresss from browser storage based on sign in
    const userEmail = JSON.parse(this.storage.getItem('userEmail')!);



    // retrieve data from the service based on the user email
    this.orderHistoryService.getOrderHistory(userEmail).subscribe(
      data => {
        this.orderHistoryList = data._embedded.orders; // asingning the data from the REST API by the service
      }
    )

  }

}
