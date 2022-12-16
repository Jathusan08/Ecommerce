import { BasketItem } from "./basket-item";

export class OrderItem {

  imageUrl: string;
  quantity: number;
  unitPrice: number;
  productId: string;

  constructor(basketItem: BasketItem) {   // constucting order item based of basket item data.

    this.imageUrl = basketItem.imageUrl;
    this.quantity = basketItem.quantity;
    this.unitPrice = basketItem.price;
    this.productId = basketItem.id;

  }






}
