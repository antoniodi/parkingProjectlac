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
	
	protected Connection conexion;
	
	// JDBC driver nombre y base de datos URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
    private static final String BD_URL= "jdbc:mysql://localhost:3306/parking";
    
    // Credenciales base de datos
	private static final String USER = "root";
	private static final String PASS= "root";
	
	public void conectar() {
		
		try {
			this.conexion = DriverManager.getConnection(BD_URL, USER, PASS);
			Class.forName(JDBC_DRIVER);			
		} catch (ClassNotFoundException | SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}
	
	public void cerrarConexion () {
		
		try {
			if (this.conexion != null && !this.conexion.isClosed()) {
				this.conexion.close();
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
		
	}
	
}
