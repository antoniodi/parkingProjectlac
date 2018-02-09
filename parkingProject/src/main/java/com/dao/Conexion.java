/**
 * 
 */
package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.dao.exception.DAOException;

/**
 * @author luis.cortes
 *
 */
public class Conexion {
	
	protected Connection dBConexion;
	
	// JDBC driver nombre y base de datos URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
    private static final String BD_URL= "jdbc:mysql://localhost:3306/parking";
    
    // Credenciales base de datos
	private static final String USER = "root";
	private static final String PASS= "root";
	
	public synchronized void conectar() {
		
		try {
			this.dBConexion = DriverManager.getConnection(BD_URL, USER, PASS);
			Class.forName(JDBC_DRIVER);			
		} catch (ClassNotFoundException | SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}
	
	public void cerrarConexion () {
		
		try {
			if (this.dBConexion != null && !this.dBConexion.isClosed()) {
				this.dBConexion.close();
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
		
	}
	
}
