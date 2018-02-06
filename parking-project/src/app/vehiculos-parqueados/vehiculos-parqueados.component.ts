import { Component, OnInit, ViewChild } from '@angular/core';
import { ServicesParking } from '../services-parking';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-vehiculos-parqueados',
  templateUrl: './vehiculos-parqueados.component.html',
  styleUrls: ['./vehiculos-parqueados.component.sass'],
  providers: [ServicesParking]
})

export class VehiculosParqueadosComponent implements OnInit {

  displayedColumns = ['tipo', 'placa', 'cicindraje', 'fecha de ingreso'];
  dataSource: MatTableDataSource<any>;
  myData: Array<any>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

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

  /**
   * Set the paginator and sort after the view init since this component will
   * be able to query its view for the initialized paginator and sort.
   */
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  ngOnInit() {
    dataSource = new MatTableDataSource(users);
  }

}
