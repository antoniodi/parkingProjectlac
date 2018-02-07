import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
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
                .catch(this.error);
    }

    error(error: any) {
        return Promise.reject(error.message || error);
    }
}
