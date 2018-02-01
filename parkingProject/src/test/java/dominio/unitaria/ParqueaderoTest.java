/**
 * 
 */
package dominio.unitaria;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import dominio.Parqueadero;
import dominio.TipoDeVehiculo;
import dominio.Vehiculo;
import dominio.exception.ParkingException;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import testdatabuilder.VehiculoTestDataBuilder;


/**
 * @author luis.cortes
 *
 */
@RunWith(JUnitParamsRunner.class)
public class ParqueaderoTest {
	
	// Vehiculos con placa que comienza por letra diferente a la A, y ingresan entre los dias lunes y domingo
	private Object[] parametersToTestVehiculoAutorizadoTodaLaSemana(){
		return new Object[] {
			new Object[] {"BLV-77D", LocalDateTime.of(2018, 1, 23, 10, 20)},
			new Object[] {"CLV-77D", LocalDateTime.of(2018, 1, 24, 10, 20)},
			new Object[] {"DLV-77D", LocalDateTime.of(2018, 1, 25, 10, 20)},
			new Object[] {"ELV-77D", LocalDateTime.of(2018, 1, 26, 10, 20)},
			new Object[] {"FLV-77D", LocalDateTime.of(2018, 1, 27, 10, 20)},
			new Object[] {"GLV-77D", LocalDateTime.of(2018, 1, 28, 10, 20)},
			new Object[] {"HLV-77D", LocalDateTime.of(2018, 1, 29, 10, 20)}
		};
	}

	@Test
	@Parameters(method = "parametersToTestVehiculoAutorizadoTodaLaSemana")
	public void vehiculoAutorizadoTodaLaSemana(String placa, LocalDateTime fechaDeIngreso) {
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		
		// Act
		try {
			
			parqueadero.validarAutorizacion(placa, fechaDeIngreso);				
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertNotEquals(Parqueadero.VEHICULO_NO_AUTORIZADO, e.getMessage());				
		}
				
	}

	// Vehiculos con placa que comienza por A, y ingresan entre los dias martes y sabado
	private Object[] parametersToTestVehiculoNoAutorizado(){
		return new Object[] {
			new Object[] {"ALV-77D", LocalDateTime.of(2018, 1, 23, 10, 20)},
			new Object[] {"ALV-77D", LocalDateTime.of(2018, 1, 24, 10, 20)},
			new Object[] {"ALV-77D", LocalDateTime.of(2018, 1, 25, 10, 20)},
			new Object[] {"ALV-77D", LocalDateTime.of(2018, 1, 26, 10, 20)},
			new Object[] {"ALV-77D", LocalDateTime.of(2018, 1, 27, 10, 20)}
		};
	}

	@Test
	@Parameters(method = "parametersToTestVehiculoNoAutorizado")
	public void vehiculoNoAutorizado(String placa, LocalDateTime fechaDeIngreso) {
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		
		// Act
		try {
			
			parqueadero.validarAutorizacion(placa, fechaDeIngreso);
			fail();
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(Parqueadero.VEHICULO_NO_AUTORIZADO, e.getMessage());
		}
				
	}
	
	// Vehiculos con placa que comienza por A, y ingresan los dias domingo y lunes
	private Object[] parametersToTestVehiculoAutorizado(){
		return new Object[] {
			new Object[] {"ALV-77D", LocalDateTime.of(2018, 1, 28, 10, 20)},
			new Object[] {"ALV-77D", LocalDateTime.of(2018, 1, 29, 10, 20)}
		};
	}
	
	@Test
	@Parameters(method = "parametersToTestVehiculoAutorizado")
	public void vehiculoAutorizado(String placa, LocalDateTime fechaDeIngreso) {
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		
		// Act
		try {
			
			parqueadero.validarAutorizacion(placa, fechaDeIngreso);
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertNotEquals(Parqueadero.VEHICULO_NO_AUTORIZADO, e.getMessage());			
		}
	}
	
	// Numero de cupos por tipo de vehiculo
	private Object[] parametersToTestNumeroDeCuposPorTipoDeVehiculo(){
		return new Object[] {
			new Object[] {TipoDeVehiculo.CARRO, 20},
			new Object[] {TipoDeVehiculo.MOTO, 10}
			//,new Object[] {null, 0}
		};
	}
	
	@Test
	@Parameters(method = "parametersToTestNumeroDeCuposPorTipoDeVehiculo")
	public void numeroDeCuposPorTipoDeVehiculo(TipoDeVehiculo tipoDeVehiculo, int expectedValue) {
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		int numeroDeCupos;
		
		// Act		
		numeroDeCupos = parqueadero.getNumeroDeCupos(tipoDeVehiculo);			
			
		// Assert
		assertEquals(expectedValue, numeroDeCupos);					
	}
	
	// Varios vehiculos con tipos de ciclindraje diferentes
		private Object[] parametersToTestvalorRecargoVehiculo(){
			return new Object[] {
				new Object[] {new VehiculoTestDataBuilder().conTipoDeVehiculo(TipoDeVehiculo.MOTO).conCilindraje(500).buildVehiculo(),
							  new BigDecimal("0")},
				new Object[] {new VehiculoTestDataBuilder().conTipoDeVehiculo(TipoDeVehiculo.MOTO).conCilindraje(501).buildVehiculo(), 
							  new BigDecimal("2000")},
				new Object[] {new VehiculoTestDataBuilder().conTipoDeVehiculo(TipoDeVehiculo.CARRO).conCilindraje(500).buildVehiculo(), 
						  new BigDecimal("0")},
				new Object[] {new VehiculoTestDataBuilder().conTipoDeVehiculo(TipoDeVehiculo.CARRO).conCilindraje(501).buildVehiculo(), 
							  new BigDecimal("0")}
			};
		}
		
		@Test
		@Parameters(method = "parametersToTestvalorRecargoVehiculo")
		public void valorRecargoVehiculo(Vehiculo vehiculo, BigDecimal expectedValue) {
			//Arrange
			Parqueadero parqueadero = new Parqueadero();
			BigDecimal valorRecargo;
			
			// Act		
			valorRecargo = parqueadero.obtenerRecargos(vehiculo);			
				
			// Assert
			assertEquals(expectedValue, valorRecargo);					
		}
	
}
