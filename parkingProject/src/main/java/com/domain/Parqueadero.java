/**
 * 
 */
package com.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;

import dominio.exception.ParkingException;

/**
 * @author luis.cortes
 *
 */
public class Parqueadero {
	
	public final String VEHICULO_NO_AUTORIZADO = "El vehiculo no esta autorizado a ingresar.";
	
	public final int CUPO_CARRO = 20;
	public int CUPO_MOTO = 10;
	
	private Parqueadero() {
		
	}
	
	public void validarAcceso(String placa, LocalDate fechaIngreso) {
		
		if (placa.toUpperCase().charAt(0) == 'A') {
			if ( !(fechaIngreso.getDayOfWeek() == DayOfWeek.SUNDAY || fechaIngreso.getDayOfWeek() == DayOfWeek.MONDAY) ) {
				throw new ParkingException(VEHICULO_NO_AUTORIZADO);
			}
		}				
	}
	

}
