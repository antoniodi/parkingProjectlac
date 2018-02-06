/**
 * 
 */
package com.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.dao.exception.DAOException;
import com.dao.interfaces.DAOHistoricoDeParqueo;


/**
 * @author luis.cortes
 *
 */
public class DAOHistoricoDeParqueoImpl extends Conexion implements DAOHistoricoDeParqueo {

	@Override
	public void eliminarHistoricoDeParqueo() {
		try {
			this.conectar(); 
			
			try (PreparedStatement st = this.dBConexion.prepareStatement(" truncate historico_de_parqueo ");) {
				st.execute();
			}
				
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
			
		} finally {
			this.cerrarConexion();
		}
		
	}

}
