/**
 * 
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

import dominio.RegistroDeIngreso;
import dominio.exception.DBException;
import interfaces.DAORegistrarIngreso;

/**
 * @author luis.cortes
 *
 */
public class DAORegistroDelIngresoImpl extends Conexion implements DAORegistrarIngreso {
	
	private static final String VEHICULO_ESTA_EN_EL_PARQUEDERO = "N";

	@Override
	public void registrarIngreso(RegistroDeIngreso registroDelIngreso) throws Exception {		
		
		try {
			this.conectar(); 
			
			try ( PreparedStatement st = this.conexion.prepareStatement("insert into historico_de_parqueo(tipo_de_vehiculo_id, placa, "
					+ "cilindraje, fecha_ingreso) values (?,?,?,?)") ) {
				
				st.setString(1, registroDelIngreso.getVehiculo().getTipoDeVehiculo().getAbreviatura());
				st.setString(2, registroDelIngreso.getVehiculo().getPlaca());
				st.setInt(3, registroDelIngreso.getVehiculo().getCilindraje());
				st.setTimestamp(4, Timestamp.valueOf(registroDelIngreso.getFechaDeIngresio()));
				st.executeUpdate();
			}
			
		} finally {
			this.cerrarConexion();
		}
		
	}

	@Override
	public void registrarSalidaVehiculo(RegistroDeIngreso registroDelIngreso) throws Exception {
		
		try {
			
			this.conectar(); 
			
			try ( PreparedStatement st = this.conexion.prepareStatement("insert into historico_de_parqueo(tipo_de_vehiculo_id, placa, "
					+ "cilindraje, fecha_ingreso) values (?,?,?,?)") ) {
				
				st.setString(1, registroDelIngreso.getVehiculo().getTipoDeVehiculo().getAbreviatura());
				st.setString(2, registroDelIngreso.getVehiculo().getPlaca());
				st.setInt(3, registroDelIngreso.getVehiculo().getCilindraje());
				st.setTimestamp(4, Timestamp.valueOf(registroDelIngreso.getFechaDeIngresio()));
				st.executeUpdate();
			}	
			
		} catch (Exception e) {
			throw e;
		} finally {
			this.cerrarConexion();
		}

	}

	@Override
	public List<RegistroDeIngreso> obtenerVehiculosParqueados() throws Exception {
		return null;
	}

}
