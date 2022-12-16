import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { BasketService } from 'src/app/services/basket.service';
import { JUEcommerceFormServiceService } from 'src/app/services/juecommerce-form-service.service';
import { Country } from 'src/app/common/country';
import { State } from 'src/app/common/state';
import { JUEcommerceValidators } from 'src/app/CustomValidator/juecommerce-validators';
import { CheckoutService } from 'src/app/services/checkout.service';
import { Router } from '@angular/router';
import { Order } from 'src/app/common/order';
import { OrderItem } from 'src/app/common/order-item';
import { Purchase } from 'src/app/common/purchase';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  checkoutFormGroup: FormGroup; // declare our formgroup this is reactive form

  storage: Storage = localStorage;

  totalPrice: number = 0;
  totalQuantity: number = 0;

  cardPaymentYears: number[] = [];
  cardPaymentMonths: number[] = [];

  countries: Country[] = [];

  shippingAddressStates: State[] = [];
  billingAddressStates: State[] = [];


  constructor(private formBuilder: FormBuilder,
    private juEcommerceFormService: JUEcommerceFormServiceService,
    private basketService: BasketService,
    private checkoutService: CheckoutService,
    private router: Router) { } // inject the form builder

  ngOnInit(): void {

    // getting the user email address from browser storage
    const userEmail = JSON.parse(this.storage.getItem('userEmail')!);


    this.checkoutFormGroup = this.formBuilder.group({

      // we use formBuilder that will build our form based on our requirements
      customer: this.formBuilder.group({
        firstName: new FormControl('', [Validators.required, Validators.minLength(2), JUEcommerceValidators.containsWhitespace]),
        lastName: new FormControl('', [Validators.required, Validators.minLength(2), JUEcommerceValidators.containsWhitespace]),
        email: new FormControl(userEmail, [Validators.required, Validators.pattern('^[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')])
      }),
      shippingAddress: this.formBuilder.group({
        street: new FormControl('', [Validators.required, Validators.minLength(2), JUEcommerceValidators.containsWhitespace]),
        city: new FormControl('', [Validators.required, Validators.minLength(2), JUEcommerceValidators.containsWhitespace]),
        state: new FormControl('', [Validators.required]),
        country: new FormControl('', [Validators.required]),
        postCode: new FormControl('', [Validators.required, Validators.minLength(2), JUEcommerceValidators.containsWhitespace])
      }),
      billingAddress: this.formBuilder.group({
        street: new FormControl('', [Validators.required, Validators.minLength(2), JUEcommerceValidators.containsWhitespace]),
        city: new FormControl('', [Validators.required, Validators.minLength(2), JUEcommerceValidators.containsWhitespace]),
        state: new FormControl('', [Validators.required]),
        country: new FormControl('', [Validators.required]),
        postCode: new FormControl('', [Validators.required, Validators.minLength(2), JUEcommerceValidators.containsWhitespace])
      }),
      paymentDetails: this.formBuilder.group({
        cardType: new FormControl('', [Validators.required]),
        nameOnCard: new FormControl('', [Validators.required, Validators.minLength(2), JUEcommerceValidators.containsWhitespace]),
        cardNumber: new FormControl('', [Validators.required, Validators.pattern('^[0-9]{16}$')]),
        securityCode: new FormControl('', [Validators.required, Validators.pattern('^[0-9]{3}$')]),
        expirationMonth: new FormControl('', [Validators.required]),
        expirationYear: new FormControl('', [Validators.required])
      }),

    });

    // populate card payment months:
    const startMonth: number = new Date().getMonth() + 1; // Months are 0 based
    console.log(`startMonth: ${startMonth}`);

    // get the card payment months
    this.juEcommerceFormService.getCardPaymentMonths(startMonth).subscribe(
      data => {
        console.log(`Retrieved card payment months: ${JSON.stringify(data)}`);
        this.cardPaymentMonths = data;
      }
    );


    // populate card payment years:

    // get the card payment years
    this.juEcommerceFormService.getCardPaymentYears().subscribe(
      data => {
        console.log(`Retrieved card payment years: ${JSON.stringify(data)}`);
        this.cardPaymentYears = data;
      }
    );

    // populate countries -  when form is initially displayed, populate countries
    this.juEcommerceFormService.getCountries().subscribe(
      data => {
        console.log(`Retrieved countries: ${JSON.stringify(data)}`);
        this.countries = data;
      }
    );

    this.reviewBasketDetails();


  }

  // crearing submit button function
  submitButton() {

    console.log("Clicked the submit button");

    // testing validation
    if (this.checkoutFormGroup.invalid) {

      this.checkoutFormGroup.markAllAsTouched(); // touching all fields triggers the display of the error messages

      //return;
    }

    console.log(`CheckoutFormGroup is valid: ${this.checkoutFormGroup.valid}`);

    // console.log(this.checkoutFormGroup.get('customer').value); // When user enter details it will display all the details as as JSON format on web browser

    // console.log(this.checkoutFormGroup.get('customer').value.email); // this is to get only email value

    // console.log(`The shipping address country is ${this.checkoutFormGroup.get('shippingAddress').value.country.name}`);
    // console.log(`The shipping address state is ${this.checkoutFormGroup.get('shippingAddress').value.state.name}`);

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // set up order

    let newOrder = new Order();

    newOrder.totalPrice = this.totalPrice;
    newOrder.totalQuantity = this.totalQuantity;

    // get basket items
    const basketItems = this.basketService.basketItems;


    // create orderItems from basketItems
    let orderItems: OrderItem[] = [];

    for (let i = 0; i < basketItems.length; i++) {

      orderItems[i] = new OrderItem(basketItems[i]);
    }

    // set up purchase
    let purchase = new Purchase();

    // populate purchase - customer
    purchase.customer = this.checkoutFormGroup.controls['customer'].value;


    purchase.customer
    // populate purchase - shipping addresss

    // JSON.stringify turns a JavaScript object into JSON text and stores that JSON text in a string.

    // JSON.parse turns a string of JSON text into a JavaScript object.

    purchase.shippingAddress = this.checkoutFormGroup.controls['shippingAddress'].value;
    const shippingState: State = JSON.parse(JSON.stringify(purchase.shippingAddress.state));
    const shippingCountry: Country = JSON.parse(JSON.stringify(purchase.shippingAddress.country));

    purchase.shippingAddress.state = shippingState.name;
    purchase.shippingAddress.country = shippingCountry.name;

    // populate purchase - billing addresss
    purchase.billingAddress = this.checkoutFormGroup.controls['billingAddress'].value;

    const billingState: State = JSON.parse(JSON.stringify(purchase.billingAddress.state));
    const billingCountry: Country = JSON.parse(JSON.stringify(purchase.billingAddress.country));

    purchase.billingAddress.state = billingState.name;
    purchase.billingAddress.country = billingCountry.name;

    // populate purchase order 

    purchase.order = newOrder;

    // populate purchase orderItems

    purchase.orderItems = orderItems;

    // time call the the REST API by the CheckoutService based on all this form data we've collected and populated

    this.checkoutService.placeOrder(purchase).subscribe({
      next: response => {
        alert(`Your order has been received.\nOrder tracking number: ${response.orderTrackingNumber}`);

        // reset the basket
        this.resetBasket();



      },

      error: err => {

        alert(`There was an error: ${err.message}`);

      }
    }
    );

    // 'next:' is success path
    // 'error:' is error pat
  }

  resetBasket() {
    // reset basket data
    this.basketService.basketItems = []; // setting the basket empty so we will have no longer previous items 
    this.basketService.totalPrice.next(0); // setting totalprice to 0
    this.basketService.totalQuantity.next(0); // setting totalquantity to 0

    this.basketService.persistBasketItems();
    // reset the form data
    this.checkoutFormGroup.reset();

    // naviage back to the products page (homepage) - once they finish with their check out
    this.router.navigateByUrl("/products");
  }


  copyShippingAddressToBillingAddress(event) {


    if (event.target.checked) {
      this.checkoutFormGroup.controls['billingAddress']
        .setValue(this.checkoutFormGroup.controls['shippingAddress'].value);

      // bug fix for states
      this.billingAddressStates = this.shippingAddressStates;

    }
    else {
      this.checkoutFormGroup.controls['billingAddress'].reset();

      // bug fix for states
      this.billingAddressStates = [];
    }



  }

  handleMonthAndYears() {
    const cardPaymentFormGroup = this.checkoutFormGroup.get('paymentDetails');


    const currentYear: number = new Date().getFullYear();
    const selectedYear: number = Number(cardPaymentFormGroup.value.expirationYear);// read the selected year from the form

    // if the current year equals the selected year, then start with current month

    let startMonth: number;

    if (currentYear === selectedYear) {

      startMonth = new Date().getMonth() + 1; // Data is zero based

    }

    else {
      startMonth = 1;
    }

    // get the card payment months
    this.juEcommerceFormService.getCardPaymentMonths(startMonth).subscribe(
      data => {
        console.log(`Retrieved card payment months: ${JSON.stringify(data)}`);
        this.cardPaymentMonths = data;

        // bug when changing to future year and changing the month for that year then coming to the present year, the month field is blank
        if (selectedYear === currentYear) {
          let selectedMonth = cardPaymentFormGroup.get('expirationMonth').value;
          if (selectedMonth < startMonth) {
            cardPaymentFormGroup.get('expirationMonth').setValue(startMonth);
          }
        }


        console.log("Debugging:  " + cardPaymentFormGroup.get('expirationMonth').value);

      }
    );

  }

  getStates(formGroupName: string) {

    const formGroup = this.checkoutFormGroup.get(formGroupName); // reading teh selcted country code from the form
    const countryCode = formGroup.value.country.code;
    const countryName = formGroup.value.country.name;

    console.log(`${formGroupName} country code: ${countryCode}`);
    console.log(`${formGroupName} country name: ${countryName}`);

    // retrieve the states for the selected country code
    this.juEcommerceFormService.getStates(countryCode).subscribe(

      data => {

        if (formGroupName === 'shippingAddress') {

          this.shippingAddressStates = data;
        }

        else {
          this.billingAddressStates = data;
        }

        // select first item by default
        formGroup.get('state').setValue(data[0]);

      }

    );

  }


  // Customer - Valdations getter methods to acess form controls
  get customerFirstName() {
    return this.checkoutFormGroup.get('customer.firstName');
  }

  get customerLastName() {
    return this.checkoutFormGroup.get('customer.lastName');
  }

  get customerEmail() {
    return this.checkoutFormGroup.get('customer.email');
  }


  // shippingAddress - Valdations getter methods to acess form controls

  get shippingAddressOfStreet() {
    return this.checkoutFormGroup.get('shippingAddress.street');
  }

  get shippingAddressOfCity() {
    return this.checkoutFormGroup.get('shippingAddress.city');
  }

  get shippingAddressOfState() {
    return this.checkoutFormGroup.get('shippingAddress.state');
  }

  get shippingAddressOfCountry() {
    return this.checkoutFormGroup.get('shippingAddress.country');
  }

  get shippingAddressOfPostCode() {
    return this.checkoutFormGroup.get('shippingAddress.postCode');
  }


  // billingAddress - Valdations getter methods to acess form controls
  get billingAddressOfStreet() {
    return this.checkoutFormGroup.get('billingAddress.street');
  }

  get billingAddressOfCity() {
    return this.checkoutFormGroup.get('billingAddress.city');
  }

  get billingAddressOfState() {
    return this.checkoutFormGroup.get('billingAddress.state');
  }

  get billingAddressOfCountry() {
    return this.checkoutFormGroup.get('billingAddress.country');
  }

  get billingAddressOfPostCode() {
    return this.checkoutFormGroup.get('billingAddress.postCode');
  }

  // paymentDetails - Valdations getter methods to acess form controls
  get paymentDetailsOfCardType() {
    return this.checkoutFormGroup.get('paymentDetails.cardType');
  }

  get paymentDetailsOfNameOnCard() {
    return this.checkoutFormGroup.get('paymentDetails.nameOnCard');
  }

  get paymentDetailsOfCardNumber() {
    return this.checkoutFormGroup.get('paymentDetails.cardNumber');
  }

  get paymentDetailsOfSecurityCode() {
    return this.checkoutFormGroup.get('paymentDetails.securityCode');
  }

  get paymentDetailsOfExpirationMonth() {
    return this.checkoutFormGroup.get('paymentDetails.expirationMonth');
  }

  get paymentDetailsOfExpirationYear() {
    return this.checkoutFormGroup.get('paymentDetails.expirationYear');
  }


  reviewBasketDetails() {

    // subscribe to BasketService.totalQuantity
    this.basketService.totalQuantity.subscribe(
      totalQuantity => this.totalQuantity = totalQuantity
    );

    // subscribe to BasketService.totalPrice
    this.basketService.totalPrice.subscribe(
      totalPrice => this.totalPrice = totalPrice
    );

  }


}
