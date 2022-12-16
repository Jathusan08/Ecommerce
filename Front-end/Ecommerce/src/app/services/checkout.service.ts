import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Purchase } from '../common/purchase';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  private purchaseUrl = `http://localhost:8080/JUEcommerce-api/checkout/purchase`;

  constructor(private httpClient: HttpClient) {

  }

  placeOrder(purchaseItems: Purchase): Observable<any> {
    return this.httpClient.post<Purchase>(this.purchaseUrl, purchaseItems);

    // sending the data using POST to the REST endpoint
  }

}
