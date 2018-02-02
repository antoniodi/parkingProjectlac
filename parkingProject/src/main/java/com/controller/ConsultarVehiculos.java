/**
 * 
 */
package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dominio.TipoDeVehiculo;
import dominio.Vehiculo;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.http.MediaType;

/**
 * @author luis.cortes
 *
 */
@RestController
public class ConsultarVehiculos {
	
	@RequestMapping(path = "/vehiculosParqueados", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Vehiculo consultarVehiculosParqueados() {
		Vehiculo v = new Vehiculo(TipoDeVehiculo.CARRO, "LIV-775", 300);
		return v; 
		
	}

}
