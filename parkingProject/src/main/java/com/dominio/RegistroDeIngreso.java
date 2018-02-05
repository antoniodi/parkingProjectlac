package com.dominio;

import java.time.LocalDateTime;

public class RegistroDeIngreso {

	private Vehiculo vehiculo;
	private LocalDateTime fechaDeIngreso;
	
	/**
	 * @param vehiculo
	 * @param fechaDeIngresio
	 */
	public RegistroDeIngreso(Vehiculo vehiculo, LocalDateTime fechaDeIngresio) {		
		this.vehiculo = vehiculo;
		this.fechaDeIngreso = fechaDeIngresio;
	}
	
	/**
	 * @return the vehiculo
	 */
	public Vehiculo getVehiculo() {
		return vehiculo;
	}
	/**
	 * @return the fechaDeIngresio
	 */
	public LocalDateTime getFechaDeIngreso() {
		return fechaDeIngreso;
	}
	
	

}
