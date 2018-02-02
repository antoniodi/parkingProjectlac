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
	
	private final String abreviaturaTipoDeVehiculo;

	private TipoDeVehiculo(String tipoDeVehiculo) {
		this.abreviaturaTipoDeVehiculo = tipoDeVehiculo;
	}
	
	public String getAbreviatura() {		
		return this.abreviaturaTipoDeVehiculo;
	}
	
}
