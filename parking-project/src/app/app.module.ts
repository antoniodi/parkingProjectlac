import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { MatCheckboxModule, MatListModule, MatIconModule, MatButtonModule,
         MatDialogModule, MatFormFieldModule, MatInputModule, MatOptionModule,
         MatSelectModule, MatSnackBarModule } from '@angular/material';

import { AppComponent } from './app.component';

import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';

import 'hammerjs';
import { VehiculosParqueadosComponent } from './vehiculos-parqueados/vehiculos-parqueados.component';
import { DialogRegistarVehiculoComponent } from './dialog-registar-vehiculo/dialog-registar-vehiculo.component';
import { DialogTicketPagoComponent } from './dialog-ticket-pago/dialog-ticket-pago.component';

@NgModule({
  declarations: [
    AppComponent,
    VehiculosParqueadosComponent,
    DialogRegistarVehiculoComponent,
    DialogTicketPagoComponent
  ],
  entryComponents: [DialogRegistarVehiculoComponent, DialogTicketPagoComponent],
  imports: [
    BrowserModule,
    HttpModule,
    BrowserAnimationsModule,
    MatCheckboxModule,
    MatListModule,
    MatIconModule,
    MatButtonModule,
    MatDialogModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatOptionModule,
    MatSelectModule,
    MatSnackBarModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
