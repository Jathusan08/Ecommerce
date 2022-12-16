import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OktaAuthStateService, OKTA_AUTH } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';
import { BasketService } from 'src/app/services/basket.service';



@Component({
  selector: 'app-login-status',
  templateUrl: './login-status.component.html',
  styleUrls: ['./login-status.component.css']
})
export class LoginStatusComponent implements OnInit {


  isAuthenticated: boolean = false;
  userFullName: string = "";
  router: Router;

  storage: Storage = localStorage; // reference to web browser's local storage - data persisted and survies browser restarts

  // storage: Storage = sessionStorage;

  constructor(private oktaAuthService: OktaAuthStateService,
    @Inject(OKTA_AUTH) private oktaAuth: OktaAuth) { // inject OktaAuth service

  }

  getUserDetails() {

    if (this.isAuthenticated) { // if is authenticated we want to get the user detail

      // Fetch the loggin in user details (user's claims)

      // user full name is exposed as a property name
      this.oktaAuth.getUser().then(
        (result) => {
          this.userFullName = result.name as string;

          // retrieve the user's email from authentication response
          const userEmail = result.email as string;

          console.log(`TESTT ${userEmail}`);

          // now store the customer email in browser storage
          this.storage.setItem('userEmail', JSON.stringify(userEmail));


        }
      );
    }
  }

  ngOnInit(): void {

    // subsrobe to authentication state changes on OktaAuth Service
    this.oktaAuthService.authState$.subscribe(
      (result) => {
        this.isAuthenticated = result.isAuthenticated!;

        this.getUserDetails(); // to get the user na,e
      }
    );

  }
  // getUserDetails() {

  //   if (this.isAuthenticated) { // if is authenticated we want to get the user detail

  //     // Fetch the loggin in user details (user's claims)

  //     // user full name is exposed as a property name
  //     this.oktaAuth.getUser().then(
  //       (result) => {
  //         this.userFullName = result.name as string;

  //         // retrieve the user's email from authentication response

  //         const customerEmail = result.email as String;

  //         // now store the customer email in browser storage
  //         this.storage.setItem('customerEmail', JSON.stringify(customerEmail));


  //       }
  //     );
  //   }
  // }

  userLogout() {
    // Clear the session
    // this.storage.clear();
    // Terminates the sesssion with Okta and removes current tokens
    this.oktaAuth.signOut();

  }




}
