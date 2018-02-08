import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { FormControl, Validators } from '@angular/forms';
import { ServicesParking } from '../services/services-parking';
import { Vehiculo } from '../dominio/vehiculo';

@Component({
  selector: 'app-dialog-ticket-pago',
  templateUrl: './dialog-ticket-pago.component.html',
  styleUrls: ['./dialog-ticket-pago.component.sass'],
  providers: [ ServicesParking ]
})
export class DialogTicketPagoComponent  {

  response: any;
  placa: string;
  tipoDeVehiculo: string;
  cilindraje: number;
  fechaIngreso: Date;
  infoTicketDePago = false;

  constructor(private servicesParking: ServicesParking, public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<DialogTicketPagoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  generarTicketDePago() {

    this.servicesParking.generarTicketDePago(this.data.placa)
      .subscribe(response => {this.response = response;
                              this.infoTicketDePago = true;
                              this.openSnackBarExito();
                              },
        error => error._body == null ? this.openSnackBarExito() :  this.openSnackBarError(error._body));
  }

  openSnackBarError(message: string) {
    this.snackBar.open(message, 'Error', {
      duration: 4000,
    });
  }

  openSnackBarExito() {
    this.snackBar.open('Ticket generado exitosamente', 'Exito', {
      duration: 4000,
    });
  }
}
