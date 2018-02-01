/**
 * 
 */
package dominio.unitaria;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import dominio.CeladorParqueadero;
import dominio.Parqueadero;
import dominio.RegistroDeIngreso;
import dominio.TipoDeVehiculo;
import dominio.Vehiculo;
import dominio.exception.ParkingException;
import services.RegistroIngreso;
import testdatabuilder.VehiculoTestDataBuilder;

/**
 * @author luis.cortes
 *
 */
public class CeladorParqueaderoTest {
	
	@Test
	public void vehiculoSeEncuentraParqueado() {
		//Arrange
		Parqueadero parqueadero = mock(Parqueadero.class);
		RegistroIngreso registroIngreso = mock(RegistroIngreso.class);
		RegistroDeIngreso registroDeIngreso = mock(RegistroDeIngreso.class);
		String placa = "ALV-77D";
		
		when(registroIngreso.obtenerRegistroDeIngresoPorPlaca(Mockito.anyString())).thenReturn(registroDeIngreso);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);		
		
		// Act
		try {
		
			celadorParqueadero.comprobarSiElVehiculoEstaParqueado(placa);
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertNotEquals(CeladorParqueadero.EL_VEHICULO_YA_SE_ENCUENTRA_ESTACIONADO, e.getMessage());			
		}				
	}	
	
	@Test
	public void vehiculoNoSeEncuentraParqueado() {
		//Arrange
		Parqueadero parqueadero = mock(Parqueadero.class);
		RegistroIngreso registroIngreso = mock(RegistroIngreso.class);
		String placa = "ALV-77D";
		
		when(registroIngreso.obtenerRegistroDeIngresoPorPlaca(Mockito.anyString())).thenReturn(null);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);		
		
		// Act
		try {
		
			celadorParqueadero.comprobarSiElVehiculoEstaParqueado(placa);
			fail();
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(CeladorParqueadero.EL_VEHICULO_YA_SE_ENCUENTRA_ESTACIONADO, e.getMessage());
		}				
	}
	
	@Test
	public void hayCuposDisponibles() {
		//Arrange
		Parqueadero parqueadero = mock(Parqueadero.class);
		RegistroIngreso registroIngreso = mock(RegistroIngreso.class);
		
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(registroIngreso.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(19);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);
		
		// Act
		try {
		
			celadorParqueadero.comprobarDisponibilidadDeCupos(TipoDeVehiculo.CARRO);
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertNotEquals(CeladorParqueadero.NO_HAY_CUPOS_DISPONIBLES, e.getMessage());			
		}				
	}
	
	@Test
	public void noHayCuposDisponibles() {
		//Arrange
		Parqueadero parqueadero = mock(Parqueadero.class);
		RegistroIngreso registroIngreso = mock(RegistroIngreso.class);
		
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(registroIngreso.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(20);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);		
		
		// Act
		try {
		
			celadorParqueadero.comprobarDisponibilidadDeCupos(TipoDeVehiculo.CARRO);
			fail();
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(CeladorParqueadero.NO_HAY_CUPOS_DISPONIBLES, e.getMessage());
		}				
	}
	
	@Test
	public void registrarVehiculoConCuposDisponibles() {
		//Arrange
		Parqueadero parqueadero = mock(Parqueadero.class);
		RegistroIngreso registroIngreso = mock(RegistroIngreso.class);

		LocalDateTime fechaIngresoMartes = LocalDateTime.of(2018, 1, 30, 10, 20);

		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conPlaca("ZIV-259").
				conTipoDeVehiculo(TipoDeVehiculo.CARRO);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo(); 
		RegistroDeIngreso registroDeIngreso = new RegistroDeIngreso(vehiculo, fechaIngresoMartes);
		
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(registroIngreso.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(19);
		when(registroIngreso.obtenerRegistroDeIngresoPorPlaca(vehiculo.getPlaca())).thenReturn(registroDeIngreso);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);		
		
		try {
			//Act
			celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertNotEquals(CeladorParqueadero.NO_HAY_CUPOS_DISPONIBLES, e.getMessage());
			
		}
			
	}
	
	@Test
	public void registrarVehiculoSinCuposDisponibles() {
		//Arrange
		Parqueadero parqueadero = mock(Parqueadero.class);
		RegistroIngreso registroIngreso = mock(RegistroIngreso.class);

		LocalDateTime fechaIngresoMartes = LocalDateTime.of(2018, 1, 29, 10, 20);

		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conPlaca("LIV-259").
				conTipoDeVehiculo(TipoDeVehiculo.CARRO);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo();	
		RegistroDeIngreso registroDeIngreso = new RegistroDeIngreso(vehiculo, fechaIngresoMartes);
		
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(registroIngreso.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(registroIngreso.obtenerRegistroDeIngresoPorPlaca(vehiculo.getPlaca())).thenReturn(registroDeIngreso);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);		
		
		try {
			//Act
			celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
			fail();
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(CeladorParqueadero.NO_HAY_CUPOS_DISPONIBLES, e.getMessage());
		}				
	}
	
	@Test
	public void registrarVehiculoAutorizado() {
		//Arrange
		Parqueadero parqueadero = mock(Parqueadero.class);
		RegistroIngreso registroIngreso = mock(RegistroIngreso.class);

		LocalDateTime fechaIngresoMartes = LocalDateTime.of(2018, 1, 29, 10, 20);

		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conPlaca("AIV-259").
				conTipoDeVehiculo(TipoDeVehiculo.CARRO);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo();	
		RegistroDeIngreso registroDeIngreso = new RegistroDeIngreso(vehiculo, fechaIngresoMartes);
		
		doNothing().when(parqueadero).validarAutorizacion(vehiculo.getPlaca(), fechaIngresoMartes);
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(registroIngreso.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(19);
		when(registroIngreso.obtenerRegistroDeIngresoPorPlaca(vehiculo.getPlaca())).thenReturn(registroDeIngreso);
		
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);		
		
		try {
			//Act
			celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);			
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertNotEquals(Parqueadero.VEHICULO_NO_AUTORIZADO, e.getMessage());			
		}				
	}
	
	@Test
	public void registrarVehiculoNoAutorizado() {
		//Arrange
		Parqueadero parqueadero = mock(Parqueadero.class);
		RegistroIngreso registroIngreso = mock(RegistroIngreso.class);

		LocalDateTime fechaIngresoLunes = LocalDateTime.of(2018, 1, 30, 10, 20);

		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conPlaca("AIV-259").
				conTipoDeVehiculo(TipoDeVehiculo.CARRO);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo();
		RegistroDeIngreso registroDeIngreso = new RegistroDeIngreso(vehiculo, fechaIngresoLunes);
		
		Mockito.doThrow(new ParkingException(Parqueadero.VEHICULO_NO_AUTORIZADO)).
				when(parqueadero).validarAutorizacion(vehiculo.getPlaca(), fechaIngresoLunes);
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(registroIngreso.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(19);
		when(registroIngreso.obtenerRegistroDeIngresoPorPlaca(vehiculo.getPlaca())).thenReturn(registroDeIngreso);			
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);
				
		try {
			//Act
			celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoLunes);
			fail();
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(Parqueadero.VEHICULO_NO_AUTORIZADO, e.getMessage());
		}				
	}

}
