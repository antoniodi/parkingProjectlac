import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { FormControl, Validators } from '@angular/forms';
import { ServicesParking } from '../services/services-parking';
import { Vehiculo } from '../dominio/vehiculo';

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
  ]);

  cilindrajeFormControl = new FormControl('', [
    Validators.required,
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
    this.servicesParking.registrarIngresoVehiculoPromise(vehiculo)
       .then(response => this.response = response
    ).catch(error => {
      this.openSnackBar(error, 'cerrar');
    });
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
    });
  }

}
