/**
 * 
 */
package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dao.services.ParkingServices;
import com.dominio.CeladorParqueadero;
import com.dominio.RegistroDeIngreso;
import com.dominio.Tarifa;
import com.dominio.TipoDeVehiculo;


import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

/**
 * @author luis.cortes
 *
 */
@RestController
public class ConsultarVehiculos {
	
//	private CeladorParqueadero celadorParqueadero;
	
	@Autowired
	ParkingServices parkingServices;
		
	@RequestMapping(path = "/vehiculosParqueados", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RegistroDeIngreso> consultarVehiculosParqueados() {
		
		return parkingServices.obtenerVehiculosParqueados(); 
	}

	@RequestMapping(path = "/tarifa", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Tarifa consultarTarifa() {
		
		return parkingServices.obtenerTrarifaPorTipoDeVehiculo(TipoDeVehiculo.CARRO); 
	}
	
	@RequestMapping(path = "/vehiculoPorPlaca", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public RegistroDeIngreso consultarVehiculoPorPlaca() {
		
		return parkingServices.obtenerRegistroDeIngresoPorPlaca("LIV-778"); 
	}
	
	@RequestMapping(path = "/numeroDeVehiculoPorPlaca", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public int consultarNumeroDeCupos() {
		
		return parkingServices.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO); 
	}

}
