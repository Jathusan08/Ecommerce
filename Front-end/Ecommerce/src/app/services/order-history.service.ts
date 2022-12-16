import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderHistory } from '../common/order-history';

@Injectable({
  providedIn: 'root'
})
export class OrderHistoryService {

  // defining the endpoint to our rest API
  private orderUrl = 'http://localhost:8080/JUEcommerce-api/orders';

  constructor(private httpClient: HttpClient) { } // inject Http client to make REST API calls

  getOrderHistory(customerEmail: string): Observable<GetResponseOrderHistory> {



    // build URL based on the customer email
    const orderHistoryUrl = `${this.orderUrl}/search/findByCustomerEmail?customerEmail=${customerEmail}&sort=dateCreated,DESC`;
    //const orderHistoryUrl = `${this.orderUrl}/search/findByCustomerEmail?customerEmail=${customerEmail}`;

    return this.httpClient.get<GetResponseOrderHistory>(orderHistoryUrl); // calls REST API

  }
}

// created interface in order to access the JSON data that's being returned
interface GetResponseOrderHistory {
  _embedded: {
    orders: OrderHistory[]; // we'll use this to access the JSON that's sent back from Spring Data REST to acess the _embedded entry.
  }

}