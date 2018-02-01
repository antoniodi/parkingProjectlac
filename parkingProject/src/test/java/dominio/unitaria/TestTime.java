package dominio.unitaria;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.Test;

public class TestTime {

	@Test
	public void test() {
		// Arrange
		Duration duracion = Duration.between(LocalDateTime.of(2018, 1, 25, 10, 0), 
											 LocalDateTime.of(2018, 1, 30, 5, 0));
		// Act
		
		// Assert
		//assertEquals(duracion.toHours(), 15);
		assertEquals(duracion.toDays(), 5);
		
	}

}
