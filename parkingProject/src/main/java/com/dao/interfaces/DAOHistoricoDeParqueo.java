/**
 * 
 */
package com.dao.interfaces;

import java.util.List;

import com.dominio.RegistroDeIngreso;

/**
 * @author luis.cortes
 *
 */
public interface DAOHistoricoDeParqueo {

	public void registrarIngreso (RegistroDeIngreso registroDelIngreso);
	public void registrarSalidaVehiculo (RegistroDeIngreso registroDelIngreso);
	public List<RegistroDeIngreso> obtenerVehiculosParqueados ();
	
}
