package services;

import dominio.Tarifa;
import dominio.TipoDeVehiculo;

public interface AdmonTarifa {
	
	public Tarifa obtenerTrarifaPorTipoDeVehiculo(TipoDeVehiculo tipoDeVehiculo);
	
}
