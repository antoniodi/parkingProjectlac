/**
 * 
 */
package dominio;

import java.time.LocalDate;

import services.RegistroIngreso;

/**
 * @author luis.cortes
 *
 */
public class CeladorParqueadero {

	private Parqueadero parqueadero;
	private RegistroIngreso registroIngreso;
	
	/**
	 * 
	 */
	public CeladorParqueadero(Parqueadero parqueadero) {
	
		this.parqueadero = parqueadero;
	}
	
	public void AtenderSolicitudParqueo(Vehiculo vehiculo, LocalDate fechaIngreso) {
		
		parqueadero.validarAutorizacion(vehiculo.getPlaca(), fechaIngreso);
		
		
		
	}

}
