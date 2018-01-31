/**
 * 
 */
package dominio;

/**
 * @author luis.cortes
 *
 */
public enum TipoDeVehiculo {
	
	CARRO("ca"), MOTO("mo");
	
	private final String abrebiaturaTipoDeVehiculo;

	private TipoDeVehiculo(String tipoDeVehiculo) {
		this.abrebiaturaTipoDeVehiculo = tipoDeVehiculo;
	}
	
	public String getTipoDeVehiculo() {		
		return this.abrebiaturaTipoDeVehiculo;
	}
	
}
