/**
 * 
 */
package dominio;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import dominio.exception.ParkingException;
import services.AdmonTarifa;
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
	private AdmonTarifa admonTarifa;
	
	/**
	 * 
	 * @param parqueadero
	 * @param registroIngreso
	 */
	public CeladorParqueadero(Parqueadero parqueadero, RegistroIngreso registroIngreso, AdmonTarifa admonTarifa) {
	
		this.parqueadero = parqueadero;
		this.registroIngreso = registroIngreso;
	}
	
	/**
	 * 
	 * @param vehiculo
	 * @param fechaIngreso
	 */
	public void atenderSolicitudDeIngreso(Vehiculo vehiculo, LocalDateTime fechaIngreso) {
		
		elVehiculoEstaParqueado(vehiculo.getPlaca());
		
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
			
		TicketDePago ticketDePago = generarTicketDePago(registroDeIngreso, fechaSalida);
		
		this.registroIngreso.registrarSalidaVehiculo(ticketDePago);
		
	}	
	
	public TicketDePago generarTicketDePago(RegistroDeIngreso registroDeIngreso, LocalDateTime fechaSalida) {					
		
		BigDecimal total = new BigDecimal(0);
		
		Duration duracion = Duration.between(registroDeIngreso.getFechaDeIngresio(), fechaSalida);
		long numeroDeDiasDeParqueo = duracion.toDays();
		long numeroDeHorasDeParqueo = duracion.toHours() - duracion.toDays()*Parqueadero.DURACION_MAXIMA_DIA_DE_PARQUEO ;
		
		if (numeroDeHorasDeParqueo >= Parqueadero.COBRO_POR_DIAS_DESDE) {
			numeroDeDiasDeParqueo += 1;
			numeroDeHorasDeParqueo = 0;
		}
		
		Tarifa tarifa = admonTarifa.obtenerTrarifaPorTipoDeVehiculo(registroDeIngreso.getVehiculo().getTipoDeVehiculo());
		
		total = total.add(tarifa.getValorDia().multiply(new BigDecimal(numeroDeDiasDeParqueo)));
		total = total.add(tarifa.getValorHora().multiply(new BigDecimal(numeroDeHorasDeParqueo)));
		total = total.add(parqueadero.obtenerRecargos(registroDeIngreso.getVehiculo()));
		
		
		return new TicketDePago(registroDeIngreso.getVehiculo(), 
								fechaSalida,
								total);
		
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
	
	public boolean elVehiculoEstaParqueado(String placa) {
		
		if (registroIngreso.obtenerRegistroDeIngresoPorPlaca(placa) != null) {
			throw new ParkingException(EL_VEHICULO_YA_SE_ENCUENTRA_ESTACIONADO);
		}
		
		return false;
	}	

}
