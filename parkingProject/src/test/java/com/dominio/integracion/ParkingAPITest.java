/**
 * 
 */
package com.dominio.integracion;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.controller.ParkingAPI;
import com.dao.DAOHistoricoDeParqueoImpl;
import com.dao.interfaces.DAOHistoricoDeParqueo;
import com.dao.services.ParkingServices;
import com.dominio.CeladorParqueadero;
import com.dominio.TicketDePago;
import com.dominio.Vehiculo;
import com.dominio.exception.ParkingException;
import com.dominio.testdatabuilder.VehiculoTestDataBuilder;

/**
 * @author luis.cortes
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class ParkingAPITest {
	
	private DAOHistoricoDeParqueo daoHistoricoDeParqueo;	
	
	@Before
	public void setUp() {
		
		this.daoHistoricoDeParqueo = new DAOHistoricoDeParqueoImpl();

	}
	
	@After
	public void tearDown() {
		
		daoHistoricoDeParqueo.eliminarHistoricoDeParqueo(); 

	}
	
	@Autowired
	ParkingAPI parkingApi;
	
	@Autowired
	ParkingServices parkingServices;
	
	
	@Test
	public void registrarIngresoVehiculoNoParqueadoAPI() {
		
		// Arrange		
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conCilindraje(501).buildVehiculo();
		ResponseEntity<String> responseExp = new ResponseEntity<String>("",HttpStatus.CREATED);
		
		// Act
		ResponseEntity < String > response = parkingApi.registrarIngresoVehiculo(vehiculo);
		
		// Assert
		Assert.assertEquals(responseExp.getStatusCode(), response.getStatusCode());
	}
	
	@Test
	public void registrarIngresoVehiculoParqueadoAPI() {
		
		// Arrange
		ResponseEntity<String> responseExp = new ResponseEntity<String>("",HttpStatus.CONFLICT);
		
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conCilindraje(501).buildVehiculo();
		parkingApi.registrarIngresoVehiculo(vehiculo);
		
		// Act
		ResponseEntity < String > response = parkingApi.registrarIngresoVehiculo(vehiculo);
		
		// Assert
		Assert.assertEquals(responseExp.getStatusCode(), response.getStatusCode());
	}
	
	@Test
	public void registrarSalidaVehiculoNoParqueadoAPI() {
		
		// Arrange		
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conCilindraje(501).buildVehiculo();
		ResponseEntity<String> responseExp = new ResponseEntity<String>("",HttpStatus.CONFLICT);
		
		
		try {
			// Act
			ResponseEntity < TicketDePago > response = parkingApi.registrarSalidaVehiculo(vehiculo.getPlaca());
		} catch (ParkingException e) {
			
			// Assert			
			Assert.assertEquals(CeladorParqueadero.EL_VEHICULO_NO_SE_ENCUENTRA_PARQUEADO, e.getMessage());
//			Assert.assertEquals(responseExp.getStatusCode(), response.getStatusCode());
		}
	}
	
	@Test
	public void registrarSalidaVehiculoParqueadoAPI() {
		
		// Arrange
		ResponseEntity<String> responseExp = new ResponseEntity<String>("",HttpStatus.ACCEPTED);
		
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conCilindraje(501).buildVehiculo();
		parkingApi.registrarIngresoVehiculo(vehiculo);
		
		// Act
		ResponseEntity < TicketDePago > response = parkingApi.registrarSalidaVehiculo(vehiculo.getPlaca());
		
		// Assert
		Assert.assertEquals(responseExp.getStatusCode(), response.getStatusCode());
	}

}
