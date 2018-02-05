package com.dao.services;

import com.dominio.Tarifa;
import com.dominio.TipoDeVehiculo;

public interface TarifaServices {
	
	public Tarifa obtenerTrarifaPorTipoDeVehiculo(TipoDeVehiculo tipoDeVehiculo);
	
}
