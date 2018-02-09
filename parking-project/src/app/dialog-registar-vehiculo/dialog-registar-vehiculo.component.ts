import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { FormControl, Validators } from '@angular/forms';
import { ServicesParking } from '../services/services-parking';
import { Vehiculo } from '../dominio/vehiculo';
const EMAIL_REGEX = /^([a-zA-Z0-9]{1,4}-[a-zA-Z0-9]{1,4})/;
@Component({
  selector: 'app-dialog-registar-vehiculo',
  templateUrl: './dialog-registar-vehiculo.component.html',
  styleUrls: ['./dialog-registar-vehiculo.component.sass'],
  providers: [ ServicesParking ]
})
export class DialogRegistarVehiculoComponent {

  response: string;
  tiposDeVehiculo = ['CARRO', 'MOTO'];
  placa: string;
  tipoDeVehiculo: string;
  cilindraje: number;

  placaFormControl = new FormControl('', [
    Validators.required,
    Validators.pattern(EMAIL_REGEX)
  ]);

  cilindrajeFormControl = new FormControl('', [
    Validators.required, Validators.min(50), Validators.max(9999),
  ]);

  tipoDeVehiculoFormControl = new FormControl('', [
    Validators.required,
  ]);

  constructor(private servicesParking: ServicesParking, public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<DialogRegistarVehiculoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  registrarVehiculo() {
    const vehiculo: Vehiculo = {'placa': this.placa,
                                'tipoDeVehiculo': this.tipoDeVehiculo,
                                'cilindraje': this.cilindraje};
    this.servicesParking.registrarIngresoVehiculo(vehiculo)
      .subscribe(response => this.response = response,
        error => error._body == null ? this.openSnackBarExito() :  this.openSnackBarError(error._body));
  }

  openSnackBarError(message: string) {
    this.snackBar.open(message, 'Error', {
      duration: 4000,
    });
  }

  openSnackBarExito() {
    this.snackBar.open('Operacion exitosa', 'Exito', {
      duration: 4000,
    });
    this.dialogRef.close();
  }

}
