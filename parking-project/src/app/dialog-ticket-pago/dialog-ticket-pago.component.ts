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

  placa: string;
  tipoDeVehiculo: string;
  cilindraje: number;
  fechaIngreso: Date;

  constructor(private servicesParking: ServicesParking, public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<DialogTicketPagoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
