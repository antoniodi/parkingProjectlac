import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VehiculosParqueadosComponent } from './vehiculos-parqueados.component';

describe('VehiculosParqueadosComponent', () => {
  let component: VehiculosParqueadosComponent;
  let fixture: ComponentFixture<VehiculosParqueadosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VehiculosParqueadosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VehiculosParqueadosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
