import { FormControl } from "@angular/forms";
import { ValidationErrors } from "@angular/forms";

export class JUEcommerceValidators {


  // whitespace validation - adding a method to check for whitespace in a field
  static containsWhitespace(controlType: FormControl): ValidationErrors {

    // the 'controlType' is which type of the form we're checking on.

    // This method is return type of 'ValidationErrors' if there is validation occurs if not I will pass as null meaning we're good to go.

    // ValidationErrors is special object which is map of errors returned from failed validation checks and it part of Angular Form API

    let val: string = controlType.value;

    // // checking if the user typed 1 character then white shpace
    // if (val.match('^[A-Z]{1}[a-z]+$')) {
    //   return null;
    // }

    // checking if string only contains whitespace
    if ((controlType.value != null) && (controlType.value.trim().length === 0)) {

      // if there is whitespace, we want to return error Object
      return { 'containsWhitespace': true };
    }

    else {

      // if there is NO whitespace, Meaning valid entry we will pass as null so it be validated
      return null;

    }

  }

}
