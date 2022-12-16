import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs'; // Reative JavaScript framework
import { of } from 'rxjs';
import { map } from "rxjs/operators";
import { Country } from '../common/country';
import { State } from '../common/state';


@Injectable({
  providedIn: 'root'
})
export class JUEcommerceFormServiceService {


  private countriesURL = "http://localhost:8080/JUEcommerce-api/countries";
  private statesURL = "http://localhost:8080/JUEcommerce-api/states";

  constructor(private httpClient: HttpClient) { } // inject HttpClient

  getCardPaymentMonths(startMonth: number): Observable<number[]> {

    let monthData: number[] = [];

    // create an array for "Month" dropdown list, starting from the current month and loopinf till the end of month 
    for (let month = startMonth; month <= 12; month++) {
      monthData.push(month); // adding months to the array
    }

    return of(monthData); // returining an Observable array. We take that normal array data and then we wrap it as an observable and we wrap it by using the 'of' operator. The 'of' operator from rxjs, will wrap an object as an Observable 

    // Remebmere we're using observable is because our angular components are going to subscribe to this given method to retrieve the data

  }


  getCardPaymentYears(): Observable<number[]> {

    // creating an array for 'Year' dropdown list
    let yearData: number[] = [];

    const startYear: number = new Date().getFullYear();
    console.log(`startYear: ${startYear}`)
    const endYear: number = startYear + 10;

    // create an array for "Year" downlist list that starts at current year and loop for next 10 years
    for (let i = startYear; i <= endYear; i++) {

      yearData.push(i);

    }

    return of(yearData);


  }


  getCountries(): Observable<Country[]> {

    // mapping the JSON data from Spring Data REST 
    return this.httpClient.get<GetResponseCountries>(this.countriesURL).pipe(
      map(response => response._embedded.countries)
    ); // Calling REST API- for getting states

  }


  getStates(countryCode: string): Observable<State[]> {

    // Search URL based on country code
    const searchStatesUrl = `${this.statesURL}/search/findByCountryCode?code=${countryCode}`;

    // mappin the JSON data from Spring Data REST
    return this.httpClient.get<GetResponseStates>(searchStatesUrl).pipe(
      map(response => response._embedded.states)
    ); // Calling REST API- for getting states

  }


}

interface GetResponseCountries {
  _embedded: {
    countries: Country[]; // unwraps the JSON from Spring Data REST _embedded entry
  }
}

interface GetResponseStates {
  _embedded: {
    states: State[]; // unwraps the JSON from Spring Data REST _embedded entry
  }
}
