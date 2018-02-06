/**
 * 
 */
package com.dominio;

import java.io.Serializable;

/**
 * @author luis.cortes
 *
 */
public class Vehiculo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TipoDeVehiculo tipoDeVehiculo;
	private String placa;
	private int cilindraje;
	
	/**
	 * @param tipoDeVehiculo
	 * @param placa
	 * @param cilindraje
	 */
	public Vehiculo(String tipoDeVehiculo, String placa, int cilindraje) {
		this.tipoDeVehiculo = Enum.valueOf(TipoDeVehiculo.class, tipoDeVehiculo);
		this.placa = placa;
		this.cilindraje = cilindraje;
	}
	
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
	
	public Vehiculo() {
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

	/**
	 * @param tipoDeVehiculo the tipoDeVehiculo to set
	 */
	public void setTipoDeVehiculo(TipoDeVehiculo tipoDeVehiculo) {
		this.tipoDeVehiculo = tipoDeVehiculo;
	}

	/**
	 * @param placa the placa to set
	 */
	public void setPlaca(String placa) {
		this.placa = placa;
	}

	/**
	 * @param cilindraje the cilindraje to set
	 */
	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
	}


}
