import { Component, OnInit, ViewChild } from '@angular/core';
import { ServicesParking } from '../services/services-parking';
import { MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import { DialogRegistarVehiculoComponent } from '../dialog-registar-vehiculo/dialog-registar-vehiculo.component';
import { DialogTicketPagoComponent } from '../dialog-ticket-pago/dialog-ticket-pago.component';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-vehiculos-parqueados',
  templateUrl: './vehiculos-parqueados.component.html',
  styleUrls: ['./vehiculos-parqueados.component.sass'],
  providers: [ServicesParking, DialogRegistarVehiculoComponent ]
})

export class VehiculosParqueadosComponent implements OnInit {

  myData: Array<any>;
  placa: string;
  tipoDeVehiculo: string;
  cilindraje: number;
  fechaIngreso: Date;

  constructor(private servicesParking: ServicesParking, public dialog: MatDialog) {
  }

  getVehiculosParqueadosPromise() {
    this.servicesParking.consultarVehiculosParqueadosPromise().then(
      myData => this.myData = myData
    );
  }

  ngOnInit() {
    this.getVehiculosParqueadosPromise();
  }

  openRegistrarIngreso(): void {
    const dialogRef = this.dialog.open(DialogRegistarVehiculoComponent, {
      width: '400px',
    });

    // Do after close
    dialogRef.afterClosed().subscribe(result => {
      this.getVehiculosParqueadosPromise();
    });
 }

  openRegistrarSalida(placa: string, tipoDeVehiculo: string, cilindraje: number): void {
    const dialogRef = this.dialog.open(DialogTicketPagoComponent, {
      width: '400px',
      data: { placa: placa, tipoDeVehiculo: tipoDeVehiculo, cilindraje: cilindraje }
    });

    // Do after close
    dialogRef.afterClosed().subscribe(result => {
      this.getVehiculosParqueadosPromise();
    });
  }

}
