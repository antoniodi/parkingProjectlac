/**
 * 
 */
package com.dominio;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.dao.services.TarifaServices;
import com.dao.services.ParkingServices;
import com.dominio.exception.ParkingException;

/**
 * @author luis.cortes
 *
 */
public class CeladorParqueadero {
	
	public static final String NO_HAY_CUPOS_DISPONIBLES = "No hay cupos disponibles.";
	public static final String EL_VEHICULO_YA_SE_ENCUENTRA_ESTACIONADO = "El vehiculo ya se encuentra estacionado.";

	private Parqueadero parqueadero;
	private ParkingServices parkingServices;	
	
	/**
	 * 
	 * @param parqueadero
	 * @param parkingServices
	 */
	public CeladorParqueadero(Parqueadero parqueadero, ParkingServices parkingServices) {
	
		this.parqueadero = parqueadero;
		this.parkingServices = parkingServices;
	}
	
	/**
	 * 
	 * @param vehiculo
	 * @param fechaIngreso
	 */
	public void atenderSolicitudDeIngreso(Vehiculo vehiculo, LocalDateTime fechaIngreso) {
		
		elVehiculoEstaParqueado(vehiculo.getPlaca());
		
		this.parqueadero.validarAutorizacion(vehiculo.getPlaca(), fechaIngreso);
		
		comprobarDisponibilidadDeCupos(vehiculo.getTipoDeVehiculo());
		
		this.parkingServices.registrarIngresoVehiculo( new RegistroDeIngreso(vehiculo, fechaIngreso));		
			
	}
	
	/**
	 * 
	 * @param placa
	 */
	public void atenderSalidaDelVehiculo(String placa, LocalDateTime fechaSalida) {
		
		RegistroDeIngreso registroDeIngreso = this.parkingServices.obtenerRegistroDeIngresoPorPlaca(placa);
			
		TicketDePago ticketDePago = generarTicketDePago(registroDeIngreso, fechaSalida);
		
		this.parkingServices.registrarSalidaVehiculo(ticketDePago);
		
	}	
	
	public TicketDePago generarTicketDePago(RegistroDeIngreso registroDeIngreso, LocalDateTime fechaSalida) {					
		
		BigDecimal total = new BigDecimal(0);
		
		Duration duracion = Duration.between(registroDeIngreso.getFechaDeIngreso(), fechaSalida);
		long numeroDeDiasDeParqueo = duracion.toDays();
		long numeroDeHorasDeParqueo = duracion.toHours() - duracion.toDays()*Parqueadero.DURACION_MAXIMA_DIA_DE_PARQUEO ;
		
		if (numeroDeHorasDeParqueo >= Parqueadero.COBRO_POR_DIAS_DESDE) {
			numeroDeDiasDeParqueo += 1;
			numeroDeHorasDeParqueo = 0;
		}
		
		Tarifa tarifa = this.parkingServices.obtenerTrarifaPorTipoDeVehiculo(registroDeIngreso.getVehiculo().getTipoDeVehiculo());
		
		total = total.add(tarifa.getValorDia().multiply(new BigDecimal(numeroDeDiasDeParqueo)));
		total = total.add(tarifa.getValorHora().multiply(new BigDecimal(numeroDeHorasDeParqueo)));
		total = total.add(this.parqueadero.obtenerRecargos(registroDeIngreso.getVehiculo()));		
		
		return new TicketDePago(registroDeIngreso.getVehiculo(), 
								fechaSalida,
								total);
		
	}
	
	/**	 
	 * @param tipoDeVehiculo
	 */
	public void comprobarDisponibilidadDeCupos(TipoDeVehiculo tipoDeVehiculo) {
		
		int numeroDeCuposDisponibles;
		
		numeroDeCuposDisponibles = this.parqueadero.getNumeroDeCupos(tipoDeVehiculo) - this.parkingServices.obtenerNumeroVehiculosParqueados(tipoDeVehiculo);
		
		if ( numeroDeCuposDisponibles < 1 ) {
			throw new ParkingException(NO_HAY_CUPOS_DISPONIBLES);
		}
	}
	
	public boolean elVehiculoEstaParqueado(String placa) {
		
		if (this.parkingServices.obtenerRegistroDeIngresoPorPlaca(placa) != null) {
			throw new ParkingException(EL_VEHICULO_YA_SE_ENCUENTRA_ESTACIONADO);
		}
		
		return false;
	}	

}
