import { Component } from '@angular/core';
import { Inject } from '@angular/core';
import { OnInit } from '@angular/core';
import { OktaAuthStateService, OKTA_AUTH } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';
import OktaSignIn from '@okta/okta-signin-widget';

import myAppConfig from 'src/app/config/my-app-config';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  oktaSignin: any;


  constructor(private oktaAuthService: OktaAuthStateService, @Inject(OKTA_AUTH) private oktaAuth: OktaAuth) { // injecting the 'OKTA_AUTH' service



    // create a new "oktaSignIn" entity
    this.oktaSignin = new OktaSignIn({
      logo: 'assets/images/JU-Eccomerce.png',
      features: {
        registration: true
      },
      baseUrl: myAppConfig.oidc.issuer.split('/oauth2')[0],
      clientId: myAppConfig.oidc.clientId,
      redirectUri: myAppConfig.oidc.redirectUri,
      authParams: {
        pkce: true,
        issuer: myAppConfig.oidc.issuer,
        scopes: myAppConfig.oidc.scopes
      }

    });
  }

  ngOnInit(): void {
    // this.oktaSignin.remove(); // remove any previous element that were rendered

    // render the sign in widget
    // 'renderEl()' is  method to render an element


    this.oktaSignin.renderEl({
      el: '#okta-sign-in-widget'
    },// " el: 'okta-sign-in-widget' " is the name that should be same as div tag id in login.component.html
      (response: any) => {
        // this is when the logs in and in return we get a response, we check if the status if is equal to 'SUCCESS'
        if (response.status === 'SUCCESS') {

          //$("#okta-sign-in-widget").hide();
          this.oktaAuth.signInWithRedirect(); // this will sign in the user accordigly and redirect them to our redirect uri.
        }
      },
      (error: any) => {
        // this is when ther an error occured during sign in
        throw error;
      }
    );

  }


  ngOnDestroy() {
    this.oktaSignin.remove();
  }

}
