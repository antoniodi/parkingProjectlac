/**
 * 
 */
package com.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.services.ParkingServices;
import com.dominio.CeladorParqueadero;
import com.dominio.RegistroDeIngreso;
import com.dominio.Tarifa;
import com.dominio.TicketDePago;
import com.dominio.TipoDeVehiculo;
import com.dominio.Vehiculo;
import com.mysql.fabric.Response;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONString;
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
		
	@RequestMapping(path = "/vehiculos-parqueados", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RegistroDeIngreso> consultarVehiculosParqueados() {
		
		return parkingServices.obtenerVehiculosParqueados(); 
	}
	
	@RequestMapping(path = "/registrar-ingresos", method = POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity < String > registrarIngresoVehiculo(@RequestBody Vehiculo vehiculo) {
		
			celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, LocalDateTime.now());			
			
			return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@RequestMapping(path = "/generar-ticket-pago/{placa}", method = PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public TicketDePago registrarSalidaVehiculo(@PathVariable(value="placa", required=true) String placa) {
		
			return celadorParqueadero.atenderSalidaDelVehiculo(placa, LocalDateTime.now());		
	}
	
	
	
	
	@RequestMapping(path = "/tarifa", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Tarifa consultarTarifa() {
		
		return parkingServices.obtenerTrarifaPorTipoDeVehiculo(TipoDeVehiculo.CARRO); 
	}
	
	@RequestMapping(path = "/vehiculo-por-placa", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public RegistroDeIngreso consultarVehiculoPorPlaca() {
		
		return parkingServices.obtenerRegistroDeIngresoPorPlaca("LIV-778"); 
	}
	
	@RequestMapping(path = "/numero-de-vehiculos-parqueados", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public int consultarNumeroDeCupos() {
		
		return parkingServices.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO); 
	}

}
