/**
 * 
 */
package dominio.unitaria;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.dao.services.ParkingServices;
import com.dominio.CeladorParqueadero;
import com.dominio.Parqueadero;
import com.dominio.RegistroDeIngreso;
import com.dominio.Tarifa;
import com.dominio.TicketDePago;
import com.dominio.TipoDeVehiculo;
import com.dominio.Vehiculo;
import com.dominio.exception.ParkingException;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import testdatabuilder.VehiculoTestDataBuilder;

/**
 * @author luis.cortes
 *
 */
@RunWith(JUnitParamsRunner.class)
public class CeladorParqueaderoTest {
	
	@Test
	public void vehiculoSeEncuentraParqueado() {
		//Arrange
		Parqueadero parqueadero = mock(Parqueadero.class);
		ParkingServices parkingServices = mock(ParkingServices.class);
		RegistroDeIngreso registroDeIngreso = mock(RegistroDeIngreso.class);
		String placa = "ALV-77D";
		
		when(parkingServices.obtenerRegistroDeIngresoPorPlaca(Mockito.anyString())).thenReturn(registroDeIngreso);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, parkingServices);		
		
		// Act
		try {

			celadorParqueadero.elVehiculoEstaParqueado(placa);
			fail();
			
		} catch (ParkingException e) {
			
			// Assert
			Assert.assertEquals(CeladorParqueadero.EL_VEHICULO_YA_SE_ENCUENTRA_ESTACIONADO, e.getMessage());
			
		}				
	}	
	
	@Test
	public void vehiculoNoSeEncuentraParqueado() {
		//Arrange
		Parqueadero parqueadero = mock(Parqueadero.class);
		ParkingServices parkingServices = mock(ParkingServices.class);
		String placa = "ALV-77D";
		
		when(parkingServices.obtenerRegistroDeIngresoPorPlaca(Mockito.anyString())).thenReturn(null);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, parkingServices);		
		
		// Act
		try {
		
			Assert.assertFalse(celadorParqueadero.elVehiculoEstaParqueado(placa));			
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(CeladorParqueadero.EL_VEHICULO_YA_SE_ENCUENTRA_ESTACIONADO, e.getMessage());
		}				
	}
	
	@Test
	public void hayCuposDisponibles() {
		//Arrange
		Parqueadero parqueadero = mock(Parqueadero.class);
		ParkingServices parkingServices = mock(ParkingServices.class);
		
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(parkingServices.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(19);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, parkingServices);
		
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
		ParkingServices parkingServices = mock(ParkingServices.class);
		
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(parkingServices.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(20);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, parkingServices);		
		
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
		ParkingServices parkingServices = mock(ParkingServices.class);

		LocalDateTime fechaIngresoMartes = LocalDateTime.of(2018, 1, 30, 10, 20);

		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conPlaca("ZIV-259").
				conTipoDeVehiculo(TipoDeVehiculo.CARRO);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo(); 
		
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(parkingServices.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(19);
		when(parkingServices.obtenerRegistroDeIngresoPorPlaca(vehiculo.getPlaca())).thenReturn(null);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, parkingServices);		
		
		//Act
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
					
		// Assert
		//Assert.assertEquals(CeladorParqueadero.NO_HAY_CUPOS_DISPONIBLES, e.getMessage());
	
	}
	
	@Test
	public void registrarVehiculoSinCuposDisponibles() {
		//Arrange
		Parqueadero parqueadero = mock(Parqueadero.class);
		ParkingServices parkingServices = mock(ParkingServices.class);

		LocalDateTime fechaIngresoMartes = LocalDateTime.of(2018, 1, 29, 10, 20);

		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conPlaca("LIV-259").
				conTipoDeVehiculo(TipoDeVehiculo.CARRO);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo();	
		
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(parkingServices.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(parkingServices.obtenerRegistroDeIngresoPorPlaca(vehiculo.getPlaca())).thenReturn(null);
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, parkingServices);		
		
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
		ParkingServices parkingServices = mock(ParkingServices.class);

		LocalDateTime fechaIngresoMartes = LocalDateTime.of(2018, 1, 29, 10, 20);

		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conPlaca("AIV-259").
				conTipoDeVehiculo(TipoDeVehiculo.CARRO);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo();	
		RegistroDeIngreso registroDeIngreso = new RegistroDeIngreso(vehiculo, fechaIngresoMartes);
		
		doNothing().when(parqueadero).validarAutorizacion(vehiculo.getPlaca(), fechaIngresoMartes);
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(parkingServices.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(19);
		when(parkingServices.obtenerRegistroDeIngresoPorPlaca(vehiculo.getPlaca())).thenReturn(registroDeIngreso);
		
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, parkingServices);		
		
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
		// Arrange
		Parqueadero parqueadero = mock(Parqueadero.class);
		ParkingServices parkingServices = mock(ParkingServices.class);

		LocalDateTime fechaIngresoLunes = LocalDateTime.of(2018, 1, 30, 10, 20);

		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conPlaca("AIV-259").
				conTipoDeVehiculo(TipoDeVehiculo.CARRO);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo();
		
		Mockito.doThrow(new ParkingException(Parqueadero.VEHICULO_NO_AUTORIZADO)).
				when(parqueadero).validarAutorizacion(vehiculo.getPlaca(), fechaIngresoLunes);
		when(parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO)).thenReturn(20);
		when(parkingServices.obtenerNumeroVehiculosParqueados(TipoDeVehiculo.CARRO)).thenReturn(19);
		when(parkingServices.obtenerRegistroDeIngresoPorPlaca(vehiculo.getPlaca())).thenReturn(null);			
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, parkingServices);
				
		try {
			// Act
			celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoLunes);
			fail();
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(Parqueadero.VEHICULO_NO_AUTORIZADO, e.getMessage());
		}				
	}
	
	// Vehiculos con diferente fecha de ingreso y salida
	private Object[] parametersToTestGenerarTicketDePago(){
		return new Object[] {
			new Object[] {new RegistroDeIngreso(new VehiculoTestDataBuilder().conCilindraje(500).buildVehiculo(),
												LocalDateTime.of(2018, 1, 29, 10, 0)),
						 LocalDateTime.of(2018, 1, 29, 10, 0),
						 new BigDecimal("0"),
						 new Tarifa(new BigDecimal(4000), new BigDecimal(500)),
						 new BigDecimal("0")},
			new Object[] {new RegistroDeIngreso(new VehiculoTestDataBuilder().conCilindraje(500).buildVehiculo(),
										    	LocalDateTime.of(2018, 1, 29, 10, 0)),
						 LocalDateTime.of(2018, 1, 29, 12, 0), 
						 new BigDecimal("0"),
						 new Tarifa(new BigDecimal(4000), new BigDecimal(500)),
						 new BigDecimal("1000")},
			new Object[] {new RegistroDeIngreso(new VehiculoTestDataBuilder().conCilindraje(650).buildVehiculo(),
						  						LocalDateTime.of(2018, 1, 29, 10, 0)),
						 LocalDateTime.of(2018, 1, 29, 20, 0), 
						 new BigDecimal("2000"),
						 new Tarifa(new BigDecimal(4000), new BigDecimal(500)),
						 new BigDecimal("6000")},
			new Object[] {new RegistroDeIngreso(new VehiculoTestDataBuilder().conCilindraje(501).buildVehiculo(),
												LocalDateTime.of(2018, 1, 29, 10, 0)),
						 LocalDateTime.of(2018, 1, 29, 12, 0), 
						 new BigDecimal("2000"),
						 new Tarifa(new BigDecimal(4000), new BigDecimal(500)),
						 new BigDecimal("3000")},
			new Object[] {new RegistroDeIngreso(new VehiculoTestDataBuilder().conTipoDeVehiculo(TipoDeVehiculo.CARRO).conCilindraje(500).buildVehiculo(),
						  						LocalDateTime.of(2018, 1, 25, 10, 0)),
					     LocalDateTime.of(2018, 1, 26, 13, 0),
					     new BigDecimal("0"),
					     new Tarifa(new BigDecimal(8000), new BigDecimal(1000)),
					     new BigDecimal("11000")},
			new Object[] {new RegistroDeIngreso(new VehiculoTestDataBuilder().conTipoDeVehiculo(TipoDeVehiculo.CARRO).conCilindraje(501).buildVehiculo(),
												LocalDateTime.of(2018, 1, 25, 10, 0)),
						 LocalDateTime.of(2018, 1, 25, 12, 0),
						 new BigDecimal("0"),
						 new Tarifa(new BigDecimal(8000), new BigDecimal(1000)),
						 new BigDecimal("2000")}
		};
	}

	@Test
	@Parameters(method = "parametersToTestGenerarTicketDePago")
	public void generarTicketDePago(RegistroDeIngreso registroDeIngreso, LocalDateTime fechaSalida, BigDecimal recargo, 
			Tarifa tarifa,BigDecimal expectedValue) throws Exception {
		// Arrange
		Parqueadero parqueadero = mock(Parqueadero.class);
		ParkingServices parkingServices = mock(ParkingServices.class);
		
		when(parqueadero.obtenerRecargos(registroDeIngreso.getVehiculo())).thenReturn(recargo);
		when(parkingServices.obtenerTrarifaPorTipoDeVehiculo(registroDeIngreso.getVehiculo().getTipoDeVehiculo())).
				thenReturn(tarifa);			
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, parkingServices);			
				
		// Act
		TicketDePago ticketDePago = celadorParqueadero.generarTicketDePago(registroDeIngreso, fechaSalida);
		
		// Assert
		Assert.assertEquals(expectedValue, ticketDePago.getTotal());
							
	}

}
