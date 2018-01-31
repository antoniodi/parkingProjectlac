/**
 * 
 */
package dominio;

import java.time.LocalDateTime;

import dominio.exception.ParkingException;
import services.RegistroIngreso;

/**
 * @author luis.cortes
 *
 */
public class CeladorParqueadero {
	
	public static final String NO_HAY_CUPOS_DISPONIBLES = "No hay cupos disponibles.";

	private Parqueadero parqueadero;
	private RegistroIngreso registroIngreso;
	
	/**
	 * 
	 */
	public CeladorParqueadero(Parqueadero parqueadero, RegistroIngreso registroIngreso) {
	
		this.parqueadero = parqueadero;
		this.registroIngreso = registroIngreso;
	}
	
	public void atenderSolicitudDeParqueo(Vehiculo vehiculo, LocalDateTime fechaIngreso) {
		
			parqueadero.validarAutorizacion(vehiculo.getPlaca(), fechaIngreso);
			
			hayCuposDisponibles(parqueadero.getNumeroDeCupos(vehiculo.getTipoDeVehiculo()),
								registroIngreso.obtenerNumeroVehiculosParqueados(vehiculo.getTipoDeVehiculo()));
			
			registroIngreso.registrarIngresoVehiculo(vehiculo, LocalDateTime.now());		
			
	}
	
	
	/**	 
	 * @param numeroDeCupos
	 * @param numeroDevehiculosEstacionados
	 * @return 
	 */
	public void hayCuposDisponibles(int numeroDeCupos, int numeroDevehiculosEstacionados) throws ParkingException {
		
		int numeroDeCuposDisponibles = numeroDeCupos - numeroDevehiculosEstacionados;
		
		if ( numeroDeCuposDisponibles < 1 ) {
			throw new ParkingException(NO_HAY_CUPOS_DISPONIBLES);
		}
	}

}
