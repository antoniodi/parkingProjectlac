/**
 * 
 */
package com.dominio;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.dominio.exception.ParkingException;

/**
 * @author luis.cortes
 *
 */
@Component
public class Parqueadero {
	
	public static final String VEHICULO_NO_AUTORIZADO = "El vehiculo no esta autorizado a ingresar.";
	public static final int COBRO_POR_DIAS_DESDE = 9;
	public static final int DURACION_MAXIMA_DIA_DE_PARQUEO = 24;
	
	private static final int CUPO_CARRO = 700;
	private static final int CUPO_MOTO = 200;	
	
	public void validarAutorizacion(String placa, LocalDateTime fechaIngreso) {
		
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
	
	/**
	 * 
	 * @param vehiculo
	 * @return valorRecargo
	 */
	public BigDecimal obtenerRecargos(Vehiculo vehiculo) {
		
		final BigDecimal VALOR_RECARGO_MOTOS_ALTO_CILINDRAJE = new BigDecimal(2000);
		final int MOTOS_BAJO_CILINDRAJE_HASTA = 500; 
		
		BigDecimal recargo = new BigDecimal(0);
		
		if (vehiculo.getTipoDeVehiculo() == TipoDeVehiculo.MOTO &&  vehiculo.getCilindraje() > MOTOS_BAJO_CILINDRAJE_HASTA) {
			recargo = recargo.add(VALOR_RECARGO_MOTOS_ALTO_CILINDRAJE);
		}
		
		return recargo;
		
	}

}
