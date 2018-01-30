/**
 * 
 */
package dominio.unitaria;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import dominio.Parqueadero;
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
		LocalDate localDateMartes = LocalDate.of(2018, 1, 30);
		
		// Act
		try {
			
			parqueadero.validarAutorizacion(placa, localDateMartes);
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
		LocalDate localDateMartes = LocalDate.of(2018, 1, 29);
		
		// Act
		try {
			
			parqueadero.validarAutorizacion(placa, localDateMartes);
			// Assert
			
		} catch (ParkingException e) {
			Assert.assertEquals(Parqueadero.VEHICULO_NO_AUTORIZADO, e.getMessage());
			fail();
		}
	}
	
	

}
