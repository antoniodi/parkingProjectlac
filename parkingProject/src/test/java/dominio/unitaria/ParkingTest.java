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
import org.springframework.beans.factory.annotation.Autowired;

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
public class ParkingTest {
	
//	@Autowired
//	private Parqueadero parqueadero;

	@Test
	public void vehiculoNoAutorizado() {
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		String placa = "ALV-77D";
		LocalDateTime fechaIngresoMartes = LocalDateTime.of(2018, 1, 30, 10, 20);
		
		// Act
		try {
			
			parqueadero.validarAutorizacion(placa, fechaIngresoMartes);
			fail();
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(Parqueadero.VEHICULO_NO_AUTORIZADO, e.getMessage());
		}
				
	}
	
	@Test
	public void vehiculoAutorizado() {
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
	public void numeroDeCuposPorTipoDeVehiculo() {
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		int numeroDeCupos;
		
		// Act		
		numeroDeCupos = parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO);			
			
		// Assert
		assertEquals(20, numeroDeCupos);;					
	}
	
	@Test
	public void hayCuposDisponibles() {
		//Arrange
		Parqueadero parqueadero = mock(Parqueadero.class);
		RegistroIngreso registroIngreso = mock(RegistroIngreso.class);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);
		
		// Act
		try {
		
			celadorParqueadero.hayCuposDisponibles(20, 19);
			
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
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);
		
		// Act
		try {
		
			celadorParqueadero.hayCuposDisponibles(20, 20);
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
				conPlaca("LIV-259").
				conTipoDeVehiculo(TipoDeVehiculo.CARRO);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo();					
		
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(registroIngreso.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(19);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);
		
		
		try {
			//Act
			celadorParqueadero.atenderSolicitudDeParqueo(vehiculo, fechaIngresoMartes);
			
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

		LocalDateTime fechaIngresoMartes = LocalDateTime.of(2018, 1, 30, 10, 20);

		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conPlaca("LIV-259").
				conTipoDeVehiculo(TipoDeVehiculo.CARRO);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo();									
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(registroIngreso.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(20);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);
		
		
		try {
			//Act
			celadorParqueadero.atenderSolicitudDeParqueo(vehiculo, fechaIngresoMartes);
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
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);
		
		
		try {
			//Act
			celadorParqueadero.atenderSolicitudDeParqueo(vehiculo, fechaIngresoMartes);			
			
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
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, registroIngreso);
		
		
		try {
			//Act
			celadorParqueadero.atenderSolicitudDeParqueo(vehiculo, fechaIngresoLunes);
			fail();
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(Parqueadero.VEHICULO_NO_AUTORIZADO, e.getMessage());
		}				
	}
	

}
