/**
 * 
 */
package com.dao.exception;

/**
 * @author luis.cortes
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DAOException(String message) {
		super(message);
	}

}
