import { TestBed } from '@angular/core/testing';

import { JUEcommerceFormServiceService } from './juecommerce-form-service.service';

describe('JUEcommerceFormServiceService', () => {
  let service: JUEcommerceFormServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JUEcommerceFormServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
