/**
 * 
 */
package com.domain;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * @author luis.cortes
 *
 */
public class Vehiculo {
	
	private TipoDeVehiculo tipoDeVehiculo;
	private String placa;
	private int cilindraje;
	
	/**
	 * @param tipoDeVehiculo
	 * @param placa
	 * @param cilindraje
	 */
	public Vehiculo(TipoDeVehiculo tipoDeVehiculo, String placa, int cilindraje) {
		this.tipoDeVehiculo = tipoDeVehiculo;
		this.placa = placa;
		this.cilindraje = cilindraje;
	}
	
	/**
	 * @return the tipoDeVehiculo
	 */
	public TipoDeVehiculo getTipoDeVehiculo() {
		return tipoDeVehiculo;
	}
	/**
	 * @return the placa
	 */
	public String getPlaca() {
		return placa;
	}
	/**
	 * @return the cilindraje
	 */
	public int getCilindraje() {
		return cilindraje;
	}
	
	
	
	

}
