/**
 * 
 */
package dominio;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author luis.cortes
 *
 */
public class TicketDePago {

	private Date horaSalida;
	private int horasDeParqueo;
	private int diasDeParqueo;
	private BigDecimal total;
	
	/**
	 * @param horaSalida
	 * @param horasDeParqueo
	 * @param diasDeParqueo
	 */
	public TicketDePago(Date horaSalida, int horasDeParqueo, int diasDeParqueo, BigDecimal total) {
		super();
		this.horaSalida = horaSalida;
		this.horasDeParqueo = horasDeParqueo;
		this.diasDeParqueo = diasDeParqueo;
		this.total = total;
	}
	
	/**
	 * @return the horaSalida
	 */
	public Date getHoraSalida() {
		return horaSalida;
	}
	/**
	 * @return the horasDeParqueo
	 */
	public int getHorasDeParqueo() {
		return horasDeParqueo;
	}
	/**
	 * @return the diasDeParqueo
	 */
	public int getDiasDeParqueo() {
		return diasDeParqueo;
	}

	/**
	 * @return the total
	 */
	public BigDecimal getTotal() {
		return total;
	}
	
	

	
	
}
