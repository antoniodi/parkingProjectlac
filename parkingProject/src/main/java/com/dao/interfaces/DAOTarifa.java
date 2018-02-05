package com.dao.interfaces;

import com.dominio.Tarifa;
import com.dominio.TipoDeVehiculo;

public interface DAOTarifa {
	public Tarifa obtenerTrarifaPorTipoDeVehiculo(TipoDeVehiculo tipoDeVehiculo);
}
