package com.dominio.unitaria;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;
import org.mockito.Mockito;

import com.dao.exception.DAOException;
import com.dao.services.ParkingServices;
import com.dominio.RegistroDeIngreso;
import com.dominio.TicketDePago;
import com.dominio.TipoDeVehiculo;
import com.dominio.Vehiculo;
import com.dominio.testdatabuilder.VehiculoTestDataBuilder;

public class ParkingServicesTest {

	@Test(expected = DAOException.class)
	public void obtenerNumeroVehiculosParqueados() {
		
		// Arrange
		ParkingServices parkingServices = mock(ParkingServices.class);
		Vehiculo vehiculo = new VehiculoTestDataBuilder().buildVehiculo();
		
		// Act
		doThrow(new DAOException("Error")).when(parkingServices).obtenerNumeroVehiculosParqueados(vehiculo.getTipoDeVehiculo());
		
		// Assert
		parkingServices.obtenerNumeroVehiculosParqueados(vehiculo.getTipoDeVehiculo());
		
	}
	
	@Test(expected = DAOException.class)
	public void obtenerRegistroDeIngresoPorPlaca() {
		
		// Arrange
		ParkingServices parkingServices = mock(ParkingServices.class);
		Vehiculo vehiculo = new VehiculoTestDataBuilder().buildVehiculo();
		
		// Act
		doThrow(new DAOException("Error")).when(parkingServices).obtenerRegistroDeIngresoPorPlaca(vehiculo.getPlaca());
		
		// Assert
		parkingServices.obtenerRegistroDeIngresoPorPlaca(vehiculo.getPlaca());
		
	}
	
	@Test(expected = DAOException.class)
	public void obtenerTrarifaPorTipoDeVehiculo() {
		
		// Arrange
		ParkingServices parkingServices = mock(ParkingServices.class);
		Vehiculo vehiculo = new VehiculoTestDataBuilder().buildVehiculo();
		
		// Act
		doThrow(new DAOException("Error")).when(parkingServices).obtenerTrarifaPorTipoDeVehiculo(vehiculo.getTipoDeVehiculo());
		
		// Assert
		parkingServices.obtenerTrarifaPorTipoDeVehiculo(vehiculo.getTipoDeVehiculo());
		
	}
	
	@Test(expected = DAOException.class)
	public void obtenerVehiculosParqueados() {
		
		// Arrange
		ParkingServices parkingServices = mock(ParkingServices.class);
		
		// Act
		doThrow(new DAOException("Error")).when(parkingServices).obtenerVehiculosParqueados();
		
		// Assert
		parkingServices.obtenerVehiculosParqueados();
		
	}
	
	@Test(expected = DAOException.class)
	public void registrarIngresoVehiculo() {
		
		// Arrange
		ParkingServices parkingServices = mock(ParkingServices.class);
		RegistroDeIngreso registroDeIngreso = new RegistroDeIngreso(new VehiculoTestDataBuilder().buildVehiculo(), 
																	LocalDateTime.now());
		
		// Act
		doThrow(new DAOException("Error")).when(parkingServices).registrarIngresoVehiculo(registroDeIngreso);
		
		// Assert
		parkingServices.registrarIngresoVehiculo(registroDeIngreso);
		
	}
	
	@Test(expected = DAOException.class)
	public void registrarSalidaVehiculo() {
		
		// Arrange
		ParkingServices parkingServices = mock(ParkingServices.class);
		TicketDePago ticketDePago = new TicketDePago(new VehiculoTestDataBuilder().buildVehiculo(), 
													 LocalDateTime.now(), 
													 new BigDecimal("1000"));
		
		// Act
		doThrow(new DAOException("Error")).when(parkingServices).registrarSalidaVehiculo(ticketDePago);
		
		// Assert
		parkingServices.registrarSalidaVehiculo(ticketDePago);
		
	}

}
