package com.dominio.exception;

/**
 * 
 * @author luis.cortes
 *
 */
public class ParkingException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

		public ParkingException(String message) {
			super(message);
		}
		
}
