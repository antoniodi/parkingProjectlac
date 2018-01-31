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
	public static final String EL_VEHICULO_YA_SE_ENCUENTRA_ESTACIONADO = "El vehiculo ya se encuentra estacionado.";

	private Parqueadero parqueadero;
	private RegistroIngreso registroIngreso;
	
	/**
	 * 
	 * @param parqueadero
	 * @param registroIngreso
	 */
	public CeladorParqueadero(Parqueadero parqueadero, RegistroIngreso registroIngreso) {
	
		this.parqueadero = parqueadero;
		this.registroIngreso = registroIngreso;
	}
	
	/**
	 * 
	 * @param vehiculo
	 * @param fechaIngreso
	 */
	public void atenderSolicitudDeIngreso(Vehiculo vehiculo, LocalDateTime fechaIngreso) {
		
		comprobarSiElVehiculoEstaEstacionado(vehiculo.getPlaca());
		
		parqueadero.validarAutorizacion(vehiculo.getPlaca(), fechaIngreso);
		
		comprobarDisponibilidadDeCupos(vehiculo.getTipoDeVehiculo());
		
		registroIngreso.registrarIngresoVehiculo(vehiculo, LocalDateTime.now());		
			
	}
	
	public void atenderSalidaDelVehiculo() {
		
	}	
	
	public double calcularValorDelParqueo() {
		return 18;
	}
	
	public void determinarTiempoDeParqueo() {
		
	}
	
	
	
	/**	 
	 * @param tipoDeVehiculo
	 */
	public void comprobarDisponibilidadDeCupos(TipoDeVehiculo tipoDeVehiculo) {
		
		int numeroDeCuposDisponibles;
		
		numeroDeCuposDisponibles = parqueadero.getNumeroDeCupos(tipoDeVehiculo) - registroIngreso.obtenerNumeroVehiculosParqueados(tipoDeVehiculo);
		
		if ( numeroDeCuposDisponibles < 1 ) {
			throw new ParkingException(NO_HAY_CUPOS_DISPONIBLES);
		}
	}
	
	public void comprobarSiElVehiculoEstaEstacionado(String placa) {
		if (registroIngreso.obtenerVehiculoParqueadoPorPlaca(placa) == null) {
			throw new ParkingException(EL_VEHICULO_YA_SE_ENCUENTRA_ESTACIONADO);
		}
	}

}
