import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogTicketPagoComponent } from './dialog-ticket-pago.component';

describe('DialogTicketPagoComponent', () => {
  let component: DialogTicketPagoComponent;
  let fixture: ComponentFixture<DialogTicketPagoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogTicketPagoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogTicketPagoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
