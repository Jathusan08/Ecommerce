import { Injectable } from '@angular/core';
import { Inject } from '@angular/core';
import { HttpEvent } from '@angular/common/http';
import { HttpHandler } from '@angular/common/http';
import { HttpInterceptor } from '@angular/common/http';
import { HttpRequest } from '@angular/common/http';
import { OKTA_AUTH } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';
import { from } from 'rxjs';
import { lastValueFrom } from 'rxjs';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  constructor(@Inject(OKTA_AUTH) private oktaAuth: OktaAuth) { } // inject OktaAuth to get the current access token for the user that's logged in.

  // overriding meethod from HttpInterceptor interface
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    // this method will intercept all of outgoing HTTP requests of HttpClient that we're making use of.

    return from(this.handleAccess(request, next)); // returns Observable
  }

  private async handleAccess(request: HttpRequest<any>, next: HttpHandler): Promise<HttpEvent<any>> {

    // only add an access token for secured endpoints
    const securedEndpoints = ['http://localhost:8080/JUEcommerce-api/orders'];

    // check to see if the secured endppoint is in the current URL that the user is making a call for.
    if (securedEndpoints.some(url => request.urlWithParams.includes(url))) {

      // get access token
      const accessToken = this.oktaAuth.getAccessToken();

      // clone the request and add new header with access token 
      // The reson why cline the request becasue is immutable meaning you can't change the request directly so the only way you can make changes here is to create copy of that request and make your changed accordingly


      request = request.clone({
        setHeaders: {
          Authorization: 'Bearer ' + accessToken
        }
      });

    }

    return await lastValueFrom(next.handle(request)); // pass the request to the next interceptor in the chain

  }
}
