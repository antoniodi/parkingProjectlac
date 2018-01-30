/**
 * 
 */
package dominio;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import dominio.exception.ParkingException;

/**
 * @author luis.cortes
 *
 */
@Component
public class Parqueadero {
	
	public static final String VEHICULO_NO_AUTORIZADO = "El vehiculo no esta autorizado a ingresar.";
	
	public static final int CUPO_CARRO = 20;
	public static final int CUPO_MOTO = 10;	
	
	public void validarAutorizacion(String placa, LocalDate fechaIngreso) {
		
		if (placa.toUpperCase().charAt(0) == 'A') {
			if ( !(fechaIngreso.getDayOfWeek() == DayOfWeek.SUNDAY || fechaIngreso.getDayOfWeek() == DayOfWeek.MONDAY) ) {
				throw new ParkingException(VEHICULO_NO_AUTORIZADO);
			}
		}				
	}
	

}
