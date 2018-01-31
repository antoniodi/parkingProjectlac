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
	public static final String VEHICULO_NO_SOPORTADO = "El parqueadero no cuenta con cupos"
			+ " para el tipo de vehiculo ingresado.";
	
	private final int CUPO_CARRO = 20;
	private final int CUPO_MOTO = 10;	
	
	public void validarAutorizacion(String placa, LocalDateTime fechaIngreso) throws ParkingException {
		
		if (placa.toUpperCase().charAt(0) == 'A') {
			if ( !(fechaIngreso.getDayOfWeek() == DayOfWeek.SUNDAY || fechaIngreso.getDayOfWeek() == DayOfWeek.MONDAY) ) {
				throw new ParkingException(VEHICULO_NO_AUTORIZADO);
			}
		}				
	}

	/**
	 * @param Tipo de Vehiculo
	 * @return número de cupos 
	 */
	public int getNumeroDeCupos(TipoDeVehiculo tipoDeVehiculo) throws ParkingException {
		switch (tipoDeVehiculo) {
		case CARRO:
			return CUPO_CARRO;
		case MOTO:
			return CUPO_MOTO;
		default:
			throw new ParkingException(VEHICULO_NO_SOPORTADO);
		}
	}
	
	

}
