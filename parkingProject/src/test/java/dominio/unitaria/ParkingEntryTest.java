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
import dominio.TipoDeVehiculo;
import dominio.Vehiculo;
import dominio.exception.ParkingException;
import services.RegistroIngreso;
import testdatabuilder.VehiculoTestDataBuilder;

/**
 * @author luis.cortes
 *
 */
public class ParkingEntryTest {
	
	@Test
	public void vehiculoSeEncuentraParqueado() {
		//Arrange
		Parqueadero parqueadero = mock(Parqueadero.class);
		RegistroIngreso registroIngreso = mock(RegistroIngreso.class);
		Vehiculo vehiculo = mock(Vehiculo.class);
		String placa = "ALV-77D";
		
		when(registroIngreso.obtenerVehiculoParqueadoPorPlaca(Mockito.anyString())).thenReturn(vehiculo);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);		
		
		// Act
		try {
		
			celadorParqueadero.comprobarSiElVehiculoEstaEstacionado(placa);
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(CeladorParqueadero.EL_VEHICULO_YA_SE_ENCUENTRA_ESTACIONADO, e.getMessage());
			fail();
		}				
	}	
	
	@Test
	public void vehiculoNoSeEncuentraParqueado() {
		//Arrange
		Parqueadero parqueadero = mock(Parqueadero.class);
		RegistroIngreso registroIngreso = mock(RegistroIngreso.class);
		Vehiculo vehiculo = mock(Vehiculo.class);
		String placa = "ALV-77D";
		
		when(registroIngreso.obtenerVehiculoParqueadoPorPlaca(Mockito.anyString())).thenReturn(null);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);		
		
		// Act
		try {
		
			celadorParqueadero.comprobarSiElVehiculoEstaEstacionado(placa);
			fail();
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(CeladorParqueadero.EL_VEHICULO_YA_SE_ENCUENTRA_ESTACIONADO, e.getMessage());
		}				
	}	
	
	@Test
	public void vehiculoAutorizadoDomingo() {
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		String placa = "ALV-77D";
		LocalDateTime fechaIngresoDomingo = LocalDateTime.of(2018, 1, 28, 10, 20);
		
		// Act
		try {
			
			parqueadero.validarAutorizacion(placa, fechaIngresoDomingo);
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(Parqueadero.VEHICULO_NO_AUTORIZADO, e.getMessage());
			fail();
		}
	}
	
	@Test
	public void vehiculoAutorizadoLunes() {
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		String placa = "ALV-77D";
		LocalDateTime fechaIngresoLunes = LocalDateTime.of(2018, 1, 29, 10, 20);
		
		// Act
		try {
			
			parqueadero.validarAutorizacion(placa, fechaIngresoLunes);
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(Parqueadero.VEHICULO_NO_AUTORIZADO, e.getMessage());
			fail();
		}
	}
	
	@Test
	public void numeroDeCuposCarro() {
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		int numeroDeCupos;
		
		// Act		
		numeroDeCupos = parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO);			
			
		// Assert
		assertEquals(20, numeroDeCupos);;					
	}
	
	@Test
	public void numeroDeCuposMoto() {
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		int numeroDeCupos;
		
		// Act		
		numeroDeCupos = parqueadero.getNumeroDeCupos(TipoDeVehiculo.MOTO);			
			
		// Assert
		assertEquals(10, numeroDeCupos);;					
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
			Assert.assertEquals(CeladorParqueadero.NO_HAY_CUPOS_DISPONIBLES, e.getMessage());
			fail();
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
		
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(registroIngreso.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(19);
		when(registroIngreso.obtenerVehiculoParqueadoPorPlaca(vehiculo.getPlaca())).thenReturn(vehiculo);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);		
		
		try {
			//Act
			celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(CeladorParqueadero.NO_HAY_CUPOS_DISPONIBLES, e.getMessage());
			fail();
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
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(registroIngreso.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(registroIngreso.obtenerVehiculoParqueadoPorPlaca(vehiculo.getPlaca())).thenReturn(vehiculo);
				
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
		
		doNothing().when(parqueadero).validarAutorizacion(vehiculo.getPlaca(), fechaIngresoMartes);
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(registroIngreso.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(19);
		when(registroIngreso.obtenerVehiculoParqueadoPorPlaca(vehiculo.getPlaca())).thenReturn(vehiculo);
		
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);		
		
		try {
			//Act
			celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);			
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(Parqueadero.VEHICULO_NO_AUTORIZADO, e.getMessage());
			fail();
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
		
		Mockito.doThrow(new ParkingException(Parqueadero.VEHICULO_NO_AUTORIZADO)).
				when(parqueadero).validarAutorizacion(vehiculo.getPlaca(), fechaIngresoLunes);
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(registroIngreso.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(19);
		when(registroIngreso.obtenerVehiculoParqueadoPorPlaca(vehiculo.getPlaca())).thenReturn(vehiculo);
				
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
