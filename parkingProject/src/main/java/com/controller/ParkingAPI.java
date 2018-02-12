/**
 * 
 */
package com.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dao.services.ParkingServices;
import com.dominio.CeladorParqueadero;
import com.dominio.RegistroDeIngreso;
import com.dominio.TicketDePago;
import com.dominio.Vehiculo;
import com.dominio.exception.ParkingException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author luis.cortes
 *
 */
@RestController
public class ParkingAPI {
	
	@Autowired
	CeladorParqueadero celadorParqueadero;
	
	@Autowired
	ParkingServices parkingServices;
	
	@CrossOrigin(origins = "http://localhost:4200")	
	@RequestMapping(path = "/vehiculos-parqueados", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< List<RegistroDeIngreso> > consultarVehiculosParqueados() {
		
		return new ResponseEntity<>(parkingServices.obtenerVehiculosParqueados(), HttpStatus.ACCEPTED);
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(path = "/registrar-ingresos", method = POST)
	public ResponseEntity < String > registrarIngresoVehiculo(@RequestBody Vehiculo vehiculo) {
		
		try {
			celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, LocalDateTime.now());			
		} catch (ParkingException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(path = "/generar-ticket-pago/{placa}", method = PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketDePago> registrarSalidaVehiculo(@PathVariable(value="placa", required=true) String placa) {				
			
		try {
			return new ResponseEntity<>(celadorParqueadero.atenderSalidaDelVehiculo(placa, LocalDateTime.now()), HttpStatus.ACCEPTED);
		} catch (ParkingException e) {
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		}
			
	}

}
