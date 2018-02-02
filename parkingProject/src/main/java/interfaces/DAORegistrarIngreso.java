/**
 * 
 */
package interfaces;

import java.util.List;

import dominio.RegistroDeIngreso;

/**
 * @author luis.cortes
 *
 */
public interface DAORegistrarIngreso {

	public void registrarIngreso (RegistroDeIngreso registroDelIngreso) throws Exception;
	public void registrarSalidaVehiculo (RegistroDeIngreso registroDelIngreso) throws Exception;
	public List<RegistroDeIngreso> obtenerVehiculosParqueados () throws Exception;
	
}
