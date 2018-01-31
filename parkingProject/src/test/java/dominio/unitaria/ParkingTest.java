/**
 * 
 */
package dominio.unitaria;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import dominio.Parqueadero;
import dominio.TipoDeVehiculo;
import dominio.exception.ParkingException;

/**
 * @author luis.cortes
 *
 */
public class ParkingTest {
	
	@Autowired
	private Parqueadero parqueadero;

	@Test
	public void vehiculoNoAutorizado() {
		//Arrange
		parqueadero = new Parqueadero();
		String placa = "ALV-77D";
		LocalDateTime localDateTimeMartes = LocalDateTime.of(2018, 1, 30, 10, 20);
		
		// Act
		try {
			
			parqueadero.validarAutorizacion(placa, localDateTimeMartes);
			fail();
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(Parqueadero.VEHICULO_NO_AUTORIZADO, e.getMessage());
		}
				
	}
	
	@Test
	public void vehiculoAutorizado() {
		//Arrange
		parqueadero = new Parqueadero();
		String placa = "ALV-77D";
		LocalDateTime localDateTimeLunes = LocalDateTime.of(2018, 1, 29, 10, 20);
		
		// Act
		try {
			
			parqueadero.validarAutorizacion(placa, localDateTimeLunes);
			// Assert
			
		} catch (ParkingException e) {
			Assert.assertEquals(Parqueadero.VEHICULO_NO_AUTORIZADO, e.getMessage());
			fail();
		}
	}
	
	@Test
	public void parqueaderoSoportaElTipoDeVehiculo() {
		//Arrange
		parqueadero = new Parqueadero();
		int numeroDeCupos;
		
		// Act
		try {
			
			numeroDeCupos = parqueadero.getNumeroDeCupos(TipoDeVehiculo.CARRO);			
			
			// Assert
			assertNotNull(numeroDeCupos);
			
		} catch (ParkingException e) {
			Assert.assertEquals(Parqueadero.VEHICULO_NO_SOPORTADO, e.getMessage());
			fail();
		}
	}
	
	

}
