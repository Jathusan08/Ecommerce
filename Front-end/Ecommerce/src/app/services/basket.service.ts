import { Injectable } from '@angular/core';
import { BehaviorSubject, ReplaySubject, Subject } from 'rxjs';
import { BasketItem } from '../common/basket-item';

@Injectable({
  providedIn: 'root'
})
export class BasketService {

  basketItems: BasketItem[] = []; // basket that will hold an array of BasketItem objects

  // subject is a subclass of observable -  we use the subject to publish events in our code. The event will be sent to all of the subscribers
  // totalPrice: Subject<number> = new Subject<number>();
  // totalQuantity: Subject<number> = new Subject<number>();

  // BehaviorSubject - keep a buffer of previous events, send previous events to new subscribers
  totalPrice: Subject<number> = new BehaviorSubject<number>(0); // '0' is the intial value for totalPrice
  totalQuantity: Subject<number> = new BehaviorSubject<number>(0); // '0' is the intial value for totalQuantity


  //storage: Storage = sessionStorage; // reference to web browser's session storage
  storage: Storage = localStorage; // reference to web browser's local storage - data persisted and survies browser restarts


  constructor() {

    // read data from storage
    let data = JSON.parse(this.storage.getItem('basketItems')!); // json.parse reads JSON String and converts to object

    if (data != null) {
      this.basketItems = data;

      // calculate totals based on the data that is read form the storage
      this.calculateBasketTotals();
    }

  }

  persistBasketItems() {

    this.storage.setItem('basketItems', JSON.stringify(this.basketItems));

  }

  addToBasket(theBasketItem: BasketItem) {

    // need to check if we already have the item in our basket
    let alreadyExistsInBasket: boolean = false;
    let existingBasketItem: BasketItem = undefined;

    if (this.basketItems.length > 0) {
      // find the item in the basket based on item id

      for (let i = 0; i < this.basketItems.length; i++) {

        if (this.basketItems[i].id === theBasketItem.id) {

          existingBasketItem = this.basketItems[i];
          break;
        }

      }

      // for(let tempBasketItem of this.basketItems){

      //       if(tempBasketItem.id === theBasketItem.id){

      //         existingBasketItem = tempBasketItem;
      //         break;
      //       }

      // }

      // to check if we found the item
      alreadyExistsInBasket = (existingBasketItem != undefined); // true or false values

    }

    if (alreadyExistsInBasket) { // if item already existed in the baskset just increment the quantity
      // increment the quantity
      existingBasketItem.quantity++;
    }

    else {
      // to add new item - just add the item to the basket array
      this.basketItems.push(theBasketItem);
    }

    // calculate basket total price and total quantity
    this.calculateBasketTotals();
  }

  calculateBasketTotals() {
    let totalPriceValue: number = 0;
    let totalQuantityValue: number = 0;

    // looping over basket items and adding values together
    for (let i = 0; i < this.basketItems.length; i++) {

      totalPriceValue += this.basketItems[i].quantity * this.basketItems[i].price;
      totalQuantityValue += this.basketItems[i].quantity;

    }

    //  for(let currentBasketItem of this.basketItems){
    //   totalPriceValue += currentBasketItem.quantity * currentBasketItem.price;
    //   totalQuantityValue += currentBasketItem.quantity;

    //  }

    // publish the new values for total price and quantity - all subsribers will receive the new data
    this.totalPrice.next(totalPriceValue); // one event for total price 
    this.totalQuantity.next(totalQuantityValue); // one event for total quantity

    // next() will send events

    // DEBUGGING- log Baskset data
    this.logBasketData(totalQuantityValue, totalPriceValue);

    // persist basket data
    this.persistBasketItems();

  }

  logBasketData(totalQuantityValue: number, totalPriceValue: number) { // debug purpose

    console.log("Contents of the cart");

    for (let i = 0; i < this.basketItems.length; i++) {

      const subTotalPrice = this.basketItems[i].quantity * this.basketItems[i].price;

      console.log(`BASKET ITEMS: ProductName: ${this.basketItems[i].name}, 
                                   ProductQuantity:${this.basketItems[i].quantity}, 
                                   subTotalPrice:${subTotalPrice}`);

    }

    console.log(`totalPrice: ${totalPriceValue.toFixed(2)}, totalQuantity: ${totalQuantityValue}`);
    console.log(`*****************************************************************************`);
  }

  decreaseQuantity(theBasketItem: BasketItem) {
    theBasketItem.quantity--; // decremnet the quantity

    if (theBasketItem.quantity === 0) {

      this.removeProduct(theBasketItem);
    }

    else {

      this.calculateBasketTotals();
    }
  }
  removeProduct(theBasketItem: BasketItem) {



    for (let i = 0; i < this.basketItems.length; i++) {

      if (this.basketItems[i].id == theBasketItem.id) {

        this.basketItems.splice(i, 1);

        this.calculateBasketTotals();
        break;

      }

    }

    // get index of item in the array
    // const productIndex = this.basketItems.findIndex(
    //   tempCartItem => tempCartItem.id == theBasketItem.id);

    //   // if found, remove the item from the array at the given index
    //   if (productIndex > -1) {
    //   this.basketItems.splice(productIndex, 1);

    //   this.calculateBasketTotals();

    //   }

  }

}
