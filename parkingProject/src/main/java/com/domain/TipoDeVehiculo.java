/**
 * 
 */
package com.domain;

/**
 * @author luis.cortes
 *
 */
public enum TipoDeVehiculo {
	
	CARRO("ca"), MOTO("mo");
	
	private final String tipoDeVehiculo;

	private TipoDeVehiculo(String tipoDeVehiculo) {
		this.tipoDeVehiculo = tipoDeVehiculo;
	}
	
	public String getTipoDeVehiculo() {		
		return this.tipoDeVehiculo;
	}
	
}
