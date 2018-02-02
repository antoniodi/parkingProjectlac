/**
 * 
 */
package dominio.exception;

/**
 * @author luis.cortes
 *
 */
public class DBException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DBException(String message) {
		super(message);
	}

}
