import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
import { HttpModule } from '@angular/http';

import { BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { MatCheckboxModule, MatListModule, MatIconModule } from '@angular/material';

import { AppComponent } from './app.component';

import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';

import 'hammerjs';
import { VehiculosParqueadosComponent } from './vehiculos-parqueados/vehiculos-parqueados.component';

@NgModule({
  declarations: [
    AppComponent,
    VehiculosParqueadosComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    BrowserAnimationsModule,
    MatCheckboxModule,
    MatListModule,
    MatIconModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
