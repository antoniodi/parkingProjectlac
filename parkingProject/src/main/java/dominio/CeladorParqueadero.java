/**
 * 
 */
package dominio;

import java.math.BigDecimal;
import java.time.Duration;
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
			
			TicketDePago ticketDePago;
			
			ticketDePago = generarTicketDePago(registroDeIngreso, fechaSalida);
			
			this.registroIngreso.registrarSalidaVehiculo(ticketDePago);
		}
		
	}	
	
	public TicketDePago generarTicketDePago(RegistroDeIngreso registroDeIngreso, LocalDateTime fechaSalida) {					
		
		BigDecimal total = new BigDecimal(0);
		Duration duracion = Duration.between(registroDeIngreso.getFechaDeIngresio(), fechaSalida);
//		long numeroDeHorasDeParqueo = ChronoUnit.HOURS.between(registroDeIngreso.getFechaDeIngresio(), fechaSalida);
//		long numeroDeDiasDeParqueo = Math.floorMod(numeroDeHorasDeParqueo, Parqueadero.COBRO_POR_DIAS_HASTA);
//		
//		long numeroDeHorasAFacturar = 10;
//		long numeroDeDiasAFacturar = 0;
//		
//		total = total.add(parqueadero.obtenerRecargos(registroDeIngreso.getVehiculo()));
//		
//		return new TicketDePago(vehiculo, fechaSalida, horasDeParqueo, diasDeParqueo, total);
		return null;
		
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
