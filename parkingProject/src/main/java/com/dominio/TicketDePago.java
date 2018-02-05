/**
 * 
 */
package com.dominio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author luis.cortes
 *
 */
public class TicketDePago {
	
	private Vehiculo vehiculo;
	private LocalDateTime fechaSalida;
	private BigDecimal total;	

	/**
	 * @param vehiculo
	 * @param fechaSalida
	 * @param total
	 */
	public TicketDePago(Vehiculo vehiculo, LocalDateTime fechaSalida, BigDecimal total) {
		
		this.vehiculo = vehiculo;
		this.fechaSalida = fechaSalida;
		this.total = total;
	}

	/**
	 * @return the vehiculo
	 */
	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	/**
	 * @return the fechaSalida
	 */
	public LocalDateTime getFechaSalida() {
		return fechaSalida;
	}

	/**
	 * @return the total
	 */
	public BigDecimal getTotal() {
		return total;
	}

}
