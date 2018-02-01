/**
 * 
 */
package dominio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author luis.cortes
 *
 */
public class TicketDePago {
	
	private Vehiculo vehiculo;
	private LocalDateTime fechaSalida;
	private long horasDeParqueo;
	private long diasDeParqueo;
	private BigDecimal total;	

	/**
	 * @param vehiculo
	 * @param fechaSalida
	 * @param horasDeParqueo
	 * @param diasDeParqueo
	 * @param total
	 */
	public TicketDePago(Vehiculo vehiculo, LocalDateTime fechaSalida, int horasDeParqueo, int diasDeParqueo,
			BigDecimal total) {
		
		this.vehiculo = vehiculo;
		this.fechaSalida = fechaSalida;
		this.horasDeParqueo = horasDeParqueo;
		this.diasDeParqueo = diasDeParqueo;
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
	 * @return the horasDeParqueo
	 */
	public long getHorasDeParqueo() {
		return horasDeParqueo;
	}

	/**
	 * @return the diasDeParqueo
	 */
	public long getDiasDeParqueo() {
		return diasDeParqueo;
	}

	/**
	 * @return the total
	 */
	public BigDecimal getTotal() {
		return total;
	}

}
