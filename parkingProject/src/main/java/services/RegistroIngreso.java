package services;

import java.time.LocalDate;

import dominio.TipoDeVehiculo;
import dominio.Vehiculo;

public interface RegistroIngreso {
	
	/**
	 * Permite obtener el número de vehiculos estacionados de un tipo especifico
	 * @param tipo de vehiculo
	 * @return número de vehiculos estacionados
	 */
	public int obtenerNumeroVehiculosParqueados(TipoDeVehiculo tipoDeVehiculo);
	
	/**
	 * Permite registrar el ingreso de un vehiculo
	 * @param Vehiculo
	 * @return fecha de ingreso
	 */
	public void registrarIngresoVehiculo(Vehiculo vehiculo, LocalDate fechaDeIngreso);

}
