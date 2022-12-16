import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  constructor(private router: Router) { } // inject/autowire the router

  ngOnInit(): void {
  }

  searchProducts(inputValue: String){
    console.log(`user provided value of: ${inputValue}`);
    this.router.navigateByUrl(`/search/${inputValue}`); // this is the router that is going to call "  {path: 'search:keyword', component: ProductListComponent},"


  }

}
