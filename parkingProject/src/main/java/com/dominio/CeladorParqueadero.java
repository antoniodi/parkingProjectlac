/**
 * 
 */
package com.dominio;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.dao.services.ParkingServices;
import com.dominio.exception.ParkingException;

/**
 * @author luis.cortes
 *
 */
@Service
public class CeladorParqueadero {
	
	public static final String NO_HAY_CUPOS_DISPONIBLES = "No hay cupos disponibles.";
	public static final String EL_VEHICULO_YA_SE_ENCUENTRA_PARQUEADO = "El vehiculo ya se encuentra parqueado.";
	public static final String EL_VEHICULO_NO_SE_ENCUENTRA_PARQUEADO = "El vehiculo no se encuentra parqueado.";
	public static final String LOS_DATOS_DEL_VEHICULOS_ESTAN_INCOMPLETOS = "Los datos del vehiculo estan incompletos.";
	

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
		
		if (vehiculo.getPlaca() == null || vehiculo.getCilindraje() == 0 || vehiculo.getTipoDeVehiculo() == null) {
			throw new ParkingException(LOS_DATOS_DEL_VEHICULOS_ESTAN_INCOMPLETOS);
		}
		
		elVehiculoEstaParqueado(vehiculo.getPlaca());
		
		this.parqueadero.validarAutorizacion(vehiculo.getPlaca(), fechaIngreso);
		
		comprobarDisponibilidadDeCupos(vehiculo.getTipoDeVehiculo());
		
		this.parkingServices.registrarIngresoVehiculo( new RegistroDeIngreso(vehiculo, fechaIngreso));		
			
	}
	
	/**
	 * 
	 * @param placa
	 */
	public TicketDePago atenderSalidaDelVehiculo(String placa, LocalDateTime fechaSalida) {
		
		RegistroDeIngreso registroDeIngreso = this.parkingServices.obtenerRegistroDeIngresoPorPlaca(placa);
		
		if ( registroDeIngreso == null) {
			throw new ParkingException(EL_VEHICULO_NO_SE_ENCUENTRA_PARQUEADO);
		}
			
		TicketDePago ticketDePago = generarTicketDePago(registroDeIngreso, fechaSalida);
		
		this.parkingServices.registrarSalidaVehiculo(ticketDePago);
		
		return ticketDePago;
		
	}	
	
	public TicketDePago generarTicketDePago(RegistroDeIngreso registroDeIngreso, LocalDateTime fechaSalida) {					
		
		BigDecimal total = new BigDecimal(0);
		long numeroDeHorasDeParqueo = 0;
		long numeroDeDiasDeParqueo = 0;
		
		Duration duracion = Duration.between(registroDeIngreso.getFechaDeIngreso(), fechaSalida);		
		
		numeroDeDiasDeParqueo = duracion.toDays();
		numeroDeHorasDeParqueo = duracion.toHours() + 1 - duracion.toDays()*Parqueadero.DURACION_MAXIMA_DIA_DE_PARQUEO ;
		
		if (numeroDeHorasDeParqueo >= Parqueadero.COBRO_POR_DIAS_DESDE) {
			numeroDeDiasDeParqueo += 1;
			numeroDeHorasDeParqueo = 0;
		}
		
		
		Tarifa tarifa = this.parkingServices.obtenerTrarifaPorTipoDeVehiculo(registroDeIngreso.getVehiculo().getTipoDeVehiculo());
		
		total = total.add(tarifa.getValorDia().multiply(new BigDecimal(numeroDeDiasDeParqueo))).
				add(tarifa.getValorHora().multiply(new BigDecimal(numeroDeHorasDeParqueo))).
				add(this.parqueadero.obtenerRecargos(registroDeIngreso.getVehiculo()));		
		
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
			throw new ParkingException(EL_VEHICULO_YA_SE_ENCUENTRA_PARQUEADO);
		}
		
		return false;
	}	

}
