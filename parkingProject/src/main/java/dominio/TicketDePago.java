/**
 * 
 */
package dominio;

import java.util.Date;

/**
 * @author luis.cortes
 *
 */
public class TicketDePago {

	private Date horaSalida;
	private int horasDeParqueo;
	private int DiasDeParqueo;
	/**
	 * @param horaSalida
	 * @param horasDeParqueo
	 * @param diasDeParqueo
	 */
	public TicketDePago(Date horaSalida, int horasDeParqueo, int diasDeParqueo) {
		super();
		this.horaSalida = horaSalida;
		this.horasDeParqueo = horasDeParqueo;
		DiasDeParqueo = diasDeParqueo;
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
		return DiasDeParqueo;
	}

	
	
}
