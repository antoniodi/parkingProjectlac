/**
 * 
 */
package com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dao.exception.DAOException;
import com.dao.interfaces.DAOTarifa;
import com.dominio.Tarifa;
import com.dominio.TipoDeVehiculo;

/**
 * @author luis.cortes
 *
 */
public class DAOTarifaImpl extends Conexion implements DAOTarifa {

	@Override
	public Tarifa obtenerTrarifaPorTipoDeVehiculo(TipoDeVehiculo tipoDeVehiculo) {		
			
		try {
			
			ResultSet resultado;
			Tarifa tarifa = null;
			
			this.conectar(); 
			
			PreparedStatement st = this.conexion.prepareStatement("select valor_dia, valor_hora from tarifa where "
					+ "tipo_de_vehiculo_id = ?");
				
			st.setString(1, tipoDeVehiculo.toString());
			resultado = st.executeQuery();
			
			
			while (resultado.next()) {
				tarifa = new Tarifa(resultado.getBigDecimal(1), resultado.getBigDecimal(2));					
			}
			
			return tarifa;
			
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			this.cerrarConexion();
		}
		
	}


}
