package com.dominio.unitaria;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.dao.exception.DAOException;
import com.dao.interfaces.DAOHistoricoDeParqueo;

public class DAOHistoricoDeParqueoImplTest {

	@Test(expected = DAOException.class)
	public void eliminarHistoricoDeParqueo() {
		// Arrange
		DAOHistoricoDeParqueo daoHistoricoDeParqueo = mock(DAOHistoricoDeParqueo.class);
		
		// Act
		doThrow(new DAOException("Error")).when(daoHistoricoDeParqueo).eliminarHistoricoDeParqueo();
		
		// Assert
		daoHistoricoDeParqueo.eliminarHistoricoDeParqueo();
		
	}
	
}
