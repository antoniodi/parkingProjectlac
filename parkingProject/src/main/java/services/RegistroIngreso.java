package services;

import java.util.List;

import dominio.RegistroDeIngreso;
import dominio.Tarifa;
import dominio.TicketDePago;
import dominio.TipoDeVehiculo;
import dominio.Vehiculo;
import interfaces.DAOTarifa;

public interface RegistroIngreso extends DAOTarifa {
	
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
	public List<RegistroDeIngreso> obtenerVehiculosParqueados();
	
	/**
	 * Permite registrar el ingreso de un vehiculo
	 * @param Vehiculo
	 * @param fechaDeIngreso
	 */
	public void registrarIngresoVehiculo(RegistroDeIngreso registroDeIngreso);	

	/**
	 * 
	 * @param placa
	 * @return Vehiculo
	 */
	public RegistroDeIngreso obtenerRegistroDeIngresoPorPlaca(String placa);
	
	/**
	 * 
	 * @param tipoDeVehiculo
	 * @return Tarifa
	 */
	public Tarifa obtenerTrarifaPorTipoDeVehiculo(TipoDeVehiculo tipoDeVehiculo);
	
	/**
	 * Permite registrar la salida de un vehiculo
	 * @param Vehiculo
	 * @param TicketDeSalida
	 */
	public void registrarSalidaVehiculo(TicketDePago tikectDePago);
	
}
