package com.dao.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.dao.Conexion;
import com.dao.exception.DAOException;
import com.dominio.RegistroDeIngreso;
import com.dominio.Tarifa;
import com.dominio.TicketDePago;
import com.dominio.TipoDeVehiculo;
import com.dominio.Vehiculo;

public class ParkingServicesImpl extends Conexion implements ParkingServices {
	
	private static final String VEHICULO_ESTA_EN_EL_PARQUEDERO = "S";
	private static final String VEHICULO_NO_ESTA_EN_EL_PARQUEDERO = "N";

	@Override
	public int obtenerNumeroVehiculosParqueados(TipoDeVehiculo tipoDeVehiculo) {
		
		ResultSet resultado;
		int numeroVehiculosParqueados = 0;
		
		try {			
			
			this.conectar(); 
			
			PreparedStatement st = this.conexion.prepareStatement("select count('X') from historico_de_parqueo where tipo_de_vehiculo_id = ? and parqueado = ? ");
			
			st.setString(1, tipoDeVehiculo.toString());
			st.setString(1, VEHICULO_ESTA_EN_EL_PARQUEDERO);
			resultado = st.executeQuery();
			
			
			while (resultado.next()) {
				numeroVehiculosParqueados = resultado.getInt(1);					
			}
			
			return numeroVehiculosParqueados;
			
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			this.cerrarConexion();
		}
	}

	@Override
	public List<RegistroDeIngreso> obtenerVehiculosParqueados() {
		
		ResultSet resultado;
		ArrayList<RegistroDeIngreso> registroDeIngresos = new ArrayList<>();
		
		try {
			
			this.conectar(); 
			
			PreparedStatement st = this.conexion.prepareStatement("select tipo_de_vehiculo_id, placa, cilindraje, fecha_ingreso "
					+ "from historico_de_parqueo where parqueado = ? ");

			st.setString(1, VEHICULO_ESTA_EN_EL_PARQUEDERO);
			resultado = st.executeQuery();
			
			
			while (resultado.next()) {
				
				registroDeIngresos.add(new RegistroDeIngreso(new Vehiculo(Enum.valueOf(TipoDeVehiculo.class, resultado.getString(1)),
						resultado.getString(2), resultado.getInt(3)), resultado.getTimestamp(4).toLocalDateTime()));
									
			}
			
			return registroDeIngresos;
			
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			this.cerrarConexion();
		}
		
	}

	@Override
	public void registrarIngresoVehiculo(RegistroDeIngreso registroDeIngreso) {
		
		try {
			
			this.conectar(); 
			
			try ( PreparedStatement st = this.conexion.prepareStatement("insert into historico_de_parqueo(tipo_de_vehiculo_id, placa, "
					+ "cilindraje, fecha_ingreso) values (?,?,?,?)") ) {
				
				st.setString(1, registroDeIngreso.getVehiculo().getTipoDeVehiculo().toString());
				st.setString(2, registroDeIngreso.getVehiculo().getPlaca());
				st.setInt(3, registroDeIngreso.getVehiculo().getCilindraje());
				st.setTimestamp(4, Timestamp.valueOf(registroDeIngreso.getFechaDeIngresio()));
				st.executeUpdate();
			}
			
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			this.cerrarConexion();
		}
		
	}

	@Override
	public RegistroDeIngreso obtenerRegistroDeIngresoPorPlaca(String placa) {
		
		ResultSet resultado;
		RegistroDeIngreso registroDeIngreso = null;
		
		try {
			
			this.conectar(); 
			
			PreparedStatement st = this.conexion.prepareStatement("select tipo_de_vehiculo_id, placa, cilindraje, fecha_ingreso "
					+ "from historico_de_parqueo where placa = ? and parqueado = ? ");
				
			st.setString(1, placa);
			st.setString(2, VEHICULO_ESTA_EN_EL_PARQUEDERO);
			resultado = st.executeQuery();
			
			
			while (resultado.next()) {
				registroDeIngreso = new RegistroDeIngreso(new Vehiculo(Enum.valueOf(TipoDeVehiculo.class, resultado.getString(1)),
						resultado.getString(2), resultado.getInt(3)), resultado.getTimestamp(4).toLocalDateTime());					
			}
			
			return registroDeIngreso;
			
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			this.cerrarConexion();
		}
	
	}

	@Override
	public Tarifa obtenerTrarifaPorTipoDeVehiculo(TipoDeVehiculo tipoDeVehiculo) {
		
		ResultSet resultado;
		Tarifa tarifa = null;
		
		try {		
			
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

	@Override
	public void registrarSalidaVehiculo(TicketDePago tikectDePago) {
		// TODO Auto-generated method stub
		
	}


}
