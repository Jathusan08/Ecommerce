import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Product } from "../common/product";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { ProductCategory } from '../common/product-category';

@Injectable({
  providedIn: 'root'
})
export class ProductService {


  private productURL = 'http://localhost:8080/JUEcommerce-api/products';

  private categoryURL = 'http://localhost:8080/JUEcommerce-api/product-category';

  constructor(private httpClient: HttpClient) { }



  getProductList(categoryId: number): Observable<Product[]> {


    // build URL based on category id
    const searchProductIdURL = `${this.productURL}/search/findByProdcutCateogryIDProductCategoryId?id=${categoryId}`;

    return this.getProducts(searchProductIdURL);

  }

  getProductCategories(): Observable<ProductCategory[]> {

    return this.httpClient.get<GetResponseProductCategory>(this.categoryURL).pipe(
      map(response => response._embedded.ProductCategory) // calling REST API

    );

  }


  searchProducts(productSearchKeyword: string): Observable<Product[]> { // Observable are some thing which receive the result of an API call until it received completely
    // build URL based on search products
    const searchProductKeyword = `${this.productURL}/search/findByNameContaining?name=${productSearchKeyword}`;

    return this.getProducts(searchProductKeyword);
  }



  private getProducts(searchProductKeyword: string): Observable<Product[]> {
    return this.httpClient.get<GetResponseProducts>(searchProductKeyword).pipe(
      map(response => response._embedded.products)
    );
  }


  getProduct(productId: number): Observable<Product> { // returning 1 product for the specific request

    // build URL based on Product Id
    const productURL = `${this.productURL}/${productId}`;

    return this.httpClient.get<Product>(productURL);

  }

  getProductListPaginate(page: number, pageSize: number, categoryId: number): Observable<GetResponseProducts> { // setting parameters for paginations

    // build URL based on cateogry id, page and size
    const pageURL = `${this.productURL}/search/findByProdcutCateogryIDProductCategoryId?id=${categoryId}&page=${page}&size=${pageSize}`;// In our back end, SPring Data REST supports pagination out of the box meaining the URL. Therefore only thing you need to send the parameters for page and size

    return this.httpClient.get<GetResponseProducts>(pageURL);

  }

  searchProductsPaginate(page: number, pageSize: number, keyword: string): Observable<GetResponseProducts> { // setting parameters for paginations

    // build URL based on keyword, page and size
    const searchURL = `${this.productURL}/search/findByNameContaining?name=${keyword}&page=${page}&size=${pageSize}`;// In our back end, SPring Data REST supports pagination out of the box meaining the URL. Therefore only thing you need to send the parameters for page and size

    return this.httpClient.get<GetResponseProducts>(searchURL);

  }




}

interface GetResponseProducts { // we'll store the categories data to when calling the REST API for 'product'

  _embedded: {
    products: Product[];
  },
  // to support the pagination meta-data
  page: {
    size: number,
    totalElements: number,
    totalPages: number,
    number: number
  }

}

interface GetResponseProductCategory { // we'll store the categories data to calling the REST API for 'product_category'

  _embedded: {
    ProductCategory: ProductCategory[];
  }
}
