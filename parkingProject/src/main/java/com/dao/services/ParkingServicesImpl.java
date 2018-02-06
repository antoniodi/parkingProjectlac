package com.dao.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dao.Conexion;
import com.dao.exception.DAOException;
import com.dominio.RegistroDeIngreso;
import com.dominio.Tarifa;
import com.dominio.TicketDePago;
import com.dominio.TipoDeVehiculo;
import com.dominio.Vehiculo;

@Service
public class ParkingServicesImpl extends Conexion implements ParkingServices {
	
	private static final String VEHICULO_ESTA_EN_EL_PARQUEDERO = "S";
	private static final String VEHICULO_NO_ESTA_EN_EL_PARQUEDERO = "N";

	@Override
	public int obtenerNumeroVehiculosParqueados(TipoDeVehiculo tipoDeVehiculo) {
		
		ResultSet resultado;
		int numeroVehiculosParqueados = 0;
		
		this.conectar(); 
		
		try (PreparedStatement st = this.dBConexion.prepareStatement("select count('X') from historico_de_parqueo "
				+ "where tipo_de_vehiculo_id = ? and parqueado = ? ")){
				
				st.setString(1, tipoDeVehiculo.toString());
				st.setString(2, VEHICULO_ESTA_EN_EL_PARQUEDERO);
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
		
		this.conectar();
		
		try (PreparedStatement st = this.dBConexion.prepareStatement("select tipo_de_vehiculo_id, placa, cilindraje, fecha_ingreso "
				+ "from historico_de_parqueo where parqueado = ? ")){
			
				st.setString(1, VEHICULO_ESTA_EN_EL_PARQUEDERO);
				resultado = st.executeQuery();
			
			while (resultado.next()) {
				
				registroDeIngresos.add(new RegistroDeIngreso(new Vehiculo(resultado.getString(1),
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
		
		this.conectar(); 
		
		try ( PreparedStatement st = this.dBConexion.prepareStatement("insert into historico_de_parqueo(tipo_de_vehiculo_id, placa, "
				+ "cilindraje, fecha_ingreso) values (?,?,?,?)") ) {
			
				st.setString(1, registroDeIngreso.getVehiculo().getTipoDeVehiculo().toString());
				st.setString(2, registroDeIngreso.getVehiculo().getPlaca());
				st.setInt(3, registroDeIngreso.getVehiculo().getCilindraje());
				st.setTimestamp(4, Timestamp.valueOf(registroDeIngreso.getFechaDeIngreso()));
				st.executeUpdate();
			
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
		
		this.conectar();
		
		try (PreparedStatement st = this.dBConexion.prepareStatement("select tipo_de_vehiculo_id, placa, cilindraje, fecha_ingreso "
				+ "from historico_de_parqueo where placa = ? and parqueado = ? ")) {
			
				st.setString(1, placa);
				st.setString(2, VEHICULO_ESTA_EN_EL_PARQUEDERO);
				resultado = st.executeQuery();						
			
			while (resultado.next()) {
				registroDeIngreso = new RegistroDeIngreso(new Vehiculo(resultado.getString(1),
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
		
		this.conectar();
		
		try (PreparedStatement st = this.dBConexion.prepareStatement("select valor_dia, valor_hora from tarifa "
				+ " where tipo_de_vehiculo_id = ?")) {
			
			
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
		
		this.conectar();
		
		try ( PreparedStatement st = this.dBConexion.prepareStatement(" update historico_de_parqueo set fecha_salida = ?, "
				+ " total = ?, parqueado = ? where placa = ? and tipo_de_vehiculo_id = ? ") ) { 
			
				st.setTimestamp(1, Timestamp.valueOf(tikectDePago.getFechaSalida()));				
				st.setBigDecimal(2, tikectDePago.getTotal());
				st.setString(3, VEHICULO_NO_ESTA_EN_EL_PARQUEDERO);
				st.setString(4, tikectDePago.getVehiculo().getPlaca());
				st.setString(5, tikectDePago.getVehiculo().getTipoDeVehiculo().toString());
				st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			this.cerrarConexion();
		}
	}

}
