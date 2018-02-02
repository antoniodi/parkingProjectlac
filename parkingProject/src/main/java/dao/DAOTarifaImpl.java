/**
 * 
 */
package dao;

import static org.assertj.core.api.Assertions.catchThrowable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dominio.Tarifa;
import dominio.TipoDeVehiculo;
import dominio.exception.DBException;
import interfaces.DAOTarifa;

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
				
			st.setString(1, tipoDeVehiculo.getAbreviatura());
			resultado = st.executeQuery();
			
			
			while (resultado.next()) {
				tarifa = new Tarifa(resultado.getBigDecimal(1), resultado.getBigDecimal(2));					
			}
			
			return tarifa;
			
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			this.cerrarConexion();
		}
		
	}


}
