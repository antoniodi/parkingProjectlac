/**
 * 
 */
package dominio;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import dominio.exception.ParkingException;

/**
 * @author luis.cortes
 *
 */
@Component
public class Parqueadero {
	
	public static final String VEHICULO_NO_AUTORIZADO = "El vehiculo no esta autorizado a ingresar.";
	
	private static final int CUPO_CARRO = 20;
	private static final int CUPO_MOTO = 10;	
	
	public void validarAutorizacion(String placa, LocalDateTime fechaIngreso) throws ParkingException {
		
		if ((placa.toUpperCase().charAt(0) == 'A') && 
			!(fechaIngreso.getDayOfWeek() == DayOfWeek.SUNDAY || fechaIngreso.getDayOfWeek() == DayOfWeek.MONDAY) ) {
			throw new ParkingException(VEHICULO_NO_AUTORIZADO);			
		}				
	}

	/**
	 * @param tipoDeVehiculo
	 * @return numeroDeCupos 
	 */
	public int getNumeroDeCupos(TipoDeVehiculo tipoDeVehiculo) {
		switch (tipoDeVehiculo) {
		case CARRO:
			return CUPO_CARRO;
		case MOTO:
			return CUPO_MOTO;
		default:
			return 0;
		}
	}
	
	

}
