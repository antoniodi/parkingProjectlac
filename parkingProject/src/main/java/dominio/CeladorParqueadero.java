/**
 * 
 */
package dominio;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import dominio.exception.ParkingException;
import services.RegistroIngreso;

/**
 * @author luis.cortes
 *
 */
public class CeladorParqueadero {
	
	public static final String NO_HAY_CUPOS_DISPONIBLES = "No hay cupos disponibles.";
	public static final String EL_VEHICULO_YA_SE_ENCUENTRA_ESTACIONADO = "El vehiculo ya se encuentra estacionado.";
	public static final String EL_VEHICULO_NO_SE_ENCUENTRA_ESTACIONADO = "El vehiculo no se encuentra estacionado.";

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
		
		comprobarSiElVehiculoEstaParqueado(vehiculo.getPlaca());
		
		parqueadero.validarAutorizacion(vehiculo.getPlaca(), fechaIngreso);
		
		comprobarDisponibilidadDeCupos(vehiculo.getTipoDeVehiculo());
		
		registroIngreso.registrarIngresoVehiculo( new RegistroDeIngreso(vehiculo, fechaIngreso));		
			
	}
	
	/**
	 * 
	 * @param placa
	 */
	public void atenderSalidaDelVehiculo(String placa, LocalDateTime fechaSalida) {
		
		RegistroDeIngreso registroDeIngreso = registroIngreso.obtenerRegistroDeIngresoPorPlaca(placa);
		
		if ( registroDeIngreso == null) {
			throw new ParkingException(EL_VEHICULO_NO_SE_ENCUENTRA_ESTACIONADO);
		} else {
			
			
		}
		
	}	
	
	public double calcularValorDelParqueo() {
		return 18;
	}
	
	public long calcularHorasDeParqueo(LocalDateTime fechaIngreso, LocalDateTime fechaSalida) {			
		
		return ChronoUnit.HOURS.between(fechaIngreso, fechaSalida);
		
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
	
	public void comprobarSiElVehiculoEstaParqueado(String placa) {
		if (registroIngreso.obtenerRegistroDeIngresoPorPlaca(placa) == null) {
			throw new ParkingException(EL_VEHICULO_YA_SE_ENCUENTRA_ESTACIONADO);
		}
	}	

}
