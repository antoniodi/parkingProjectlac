package services;

import java.time.LocalDateTime;
import java.util.List;

import dominio.RegistroDeIngreso;
import dominio.TicketDePago;
import dominio.TipoDeVehiculo;
import dominio.Vehiculo;

public interface RegistroIngreso {
	
	/**
	 * Permite obtener el numero de vehiculos estacionados de un tipo especifico
	 * @param tipoDeVehiculo
	 * @return numeroDeVehiculosEstacionados
	 */
	public int obtenerNumeroVehiculosParqueados(TipoDeVehiculo tipoDeVehiculo);
	
	/**
	 * Permite obtener los vehiculos estacionados
	 * @return vehiculosEstacionados
	 */
	public List<Vehiculo> obtenerVehiculosParqueados();
	
	/**
	 * Permite registrar el ingreso de un vehiculo
	 * @param Vehiculo
	 * @param fechaDeIngreso
	 */
	public void registrarIngresoVehiculo(RegistroDeIngreso registroDeIngreso);
	
	/**
	 * Permite registrar la salida de un vehiculo
	 * @param Vehiculo
	 * @param TicketDeSalida
	 */
	public void registrarSalidaVehiculo(Vehiculo vehiculo, TicketDePago tikectDePago);

	/**
	 * 
	 * @param placa
	 * @return Vehiculo
	 */
	public RegistroDeIngreso obtenerRegistroDeIngresoPorPlaca(String placa);
}
