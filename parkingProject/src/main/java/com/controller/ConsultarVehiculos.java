/**
 * 
 */
package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.services.ParkingServices;
import com.dao.services.ParkingServicesImpl;
import com.dominio.CeladorParqueadero;
import com.dominio.Parqueadero;
import com.dominio.RegistroDeIngreso;
import com.dominio.Tarifa;
import com.dominio.TipoDeVehiculo;
import com.dominio.Vehiculo;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.http.MediaType;

/**
 * @author luis.cortes
 *
 */
@RestController
public class ConsultarVehiculos {
	
	private CeladorParqueadero celadorParqueadero;
	
	@RequestMapping(path = "/vehiculosParqueados", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RegistroDeIngreso> consultarVehiculosParqueados() {
		
		ParkingServices parkingServices = new ParkingServicesImpl();
		
		celadorParqueadero = new CeladorParqueadero(new Parqueadero(), new ParkingServicesImpl());
		
		return parkingServices.obtenerVehiculosParqueados(); 
	}

	@RequestMapping(path = "/Tarifa", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Tarifa consultarTarifa() {
		
		ParkingServices parkingServices = new ParkingServicesImpl();
		
		celadorParqueadero = new CeladorParqueadero(new Parqueadero(), new ParkingServicesImpl());
		
		return parkingServices.obtenerTrarifaPorTipoDeVehiculo(TipoDeVehiculo.CARRO); 
	}

}
