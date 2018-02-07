import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogRegistarVehiculoComponent } from './dialog-registar-vehiculo.component';

describe('DialogRegistarVehiculoComponent', () => {
  let component: DialogRegistarVehiculoComponent;
  let fixture: ComponentFixture<DialogRegistarVehiculoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogRegistarVehiculoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogRegistarVehiculoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
