import { Product } from "./product";

export class BasketItem {

  id: string;
  name: string;
  imageUrl: string;
  price: number;
  quantity: number;
  ProductQuantity: number;


  constructor(product: Product) { // passing the product type and assigning to our BasketItem property

    this.id = product.productId;
    this.name = product.name;
    this.imageUrl = product.imageURL;
    this.price = product.unitPrice;
    this.quantity = 1;
    this.ProductQuantity = product.unitsInStock;

  }


}
