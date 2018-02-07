import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Vehiculo } from '../dominio/vehiculo';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class ServicesParking {

    constructor (private http: Http) {}

    public consultarVehiculosParqueados() {
        const url = 'http://localhost:8080/vehiculos-parqueados';
        return this.http.get(url)
                .map(response => response.json());
    }

    public consultarVehiculosParqueadosPromise() {
        const url = 'http://localhost:8080/vehiculos-parqueados';
        return this.http.get(url)
                .toPromise()
                .then(response => response.json())
                .catch(this.handleError);
    }

    public registrarIngresoVehiculoPromise(vehiculo: Vehiculo) {
        const url = 'http://localhost:8080/registrar-ingresos';
        return this.http.post(url, vehiculo)
                .toPromise()
                .then(response => response.text() ? response.json() : response.text())
                .catch(this.handleError);
    }

    public registrarSalidaVehiculoPromise(vehiculo: Vehiculo) {
        const url = 'http://localhost:8080/registrar-ingresos';
        return this.http.post(url, vehiculo)
                .toPromise()
                .then(response => response.text() ? response.json() : response.text())
                .catch(this.handleError);
    }


      private handleError (error: Response | any) {
        // In a real world app, you might use a remote logging infrastructure
        let errMsg: string;
        if (error instanceof Response) {
          const body: any = error.json() || '';
          const err = body.error || JSON.stringify(body);
          errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
        } else {
          errMsg = error.message ? error.message : error.toString();
        }
        console.error('preuba' + errMsg);
        return Promise.reject(errMsg);
      }
}
