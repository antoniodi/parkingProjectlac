import { Http } from '@angular/http';
import { Injectable } from '@angular/core';

@Injectable()
export class ServicesParking {

    constructor (private http: Http) {}

    public consultarVehiculosParqueados() {
        return this.http.get('http://localhost:8080/vehiculos-parqueados')
                .map(response => response.json());
    }
}
