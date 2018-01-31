/**
 * 
 */
package dominio.unitaria;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dominio.Parqueadero;
import dominio.exception.ParkingException;

/**
 * @author luis.cortes
 *
 */
@RunWith(value = Parameterized.class)
public class ParkingAutorizationTest {

	@Parameters
	public static Iterable<Object[]> getData(){
		return Arrays.asList(new Object[][] {
			{"ALV-77D", LocalDateTime.of(2018, 1, 23, 10, 20)},
			{"ALV-77D", LocalDateTime.of(2018, 1, 24, 10, 20)},
			{"ALV-77D", LocalDateTime.of(2018, 1, 25, 10, 20)},
			{"ALV-77D", LocalDateTime.of(2018, 1, 26, 10, 20)},
			{"ALV-77D", LocalDateTime.of(2018, 1, 27, 10, 20)}
		});
	}

	private String placa;
	private LocalDateTime fechaDeIngreso;
	
	/**
	 * @param placa
	 * @param localDateTime
	 */
	public ParkingAutorizationTest(String placa, LocalDateTime fechaDeIngreso) {
		this.placa = placa;
		this.fechaDeIngreso = fechaDeIngreso;
	}

	@Test
	public void vehiculoNoAutorizado() {
		//Arrange
		Parqueadero parqueadero = new Parqueadero();
		
		// Act
		try {
			
			parqueadero.validarAutorizacion(this.placa, this.fechaDeIngreso);
			fail();
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(Parqueadero.VEHICULO_NO_AUTORIZADO, e.getMessage());
		}
				
	}

}
