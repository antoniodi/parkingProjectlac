import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';
import { Response } from '@angular/http/src/static_response';
import { Observable } from 'rxjs/Observable';
import { ServicesParking } from '../services-parking';

@Component({
  selector: 'app-vehiculos-parqueados',
  templateUrl: './vehiculos-parqueados.component.html',
  styleUrls: ['./vehiculos-parqueados.component.sass'],
  providers: [ServicesParking]
})

export class VehiculosParqueadosComponent implements OnInit {

  myData: Array<any>;

  constructor(private servicesParking: ServicesParking) {
    this.getVehiculosParqueados();
  }

  getVehiculosParqueados() {
    this.servicesParking.consultarVehiculosParqueados()
      .subscribe(res => this.myData = res,
        error => alert(error),
        () => console.log('final')
      );
  }

  ngOnInit() {
  }

}
