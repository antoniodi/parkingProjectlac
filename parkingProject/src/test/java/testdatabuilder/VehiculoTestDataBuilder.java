/**
 * 
 */
package testdatabuilder;

import com.dominio.TipoDeVehiculo;
import com.dominio.Vehiculo;

/**
 * @author luis.cortes
 *
 */
public class VehiculoTestDataBuilder {
	
	private final TipoDeVehiculo TIPODEVEHICULO = TipoDeVehiculo.CARRO;
	private final String PLACA = "LIV-777";
	private final int CILINDRAJE = 100;
	
	private TipoDeVehiculo tipoDeVehiculo;
	private String placa;
	private int cilindraje;
	
	/**
	 * @param tipoDeVehiculo
	 * @param placa
	 * @param cilindraje
	 */
	public VehiculoTestDataBuilder() {
		this.tipoDeVehiculo = TIPODEVEHICULO;
		this.placa = PLACA;
		this.cilindraje = CILINDRAJE;
	}

	/**
	 * @param tipoDeVehiculo the tipoDeVehiculo to set
	 * @return VehiculoTestDataBuilder
	 */
	public VehiculoTestDataBuilder conTipoDeVehiculo(TipoDeVehiculo tipoDeVehiculo) {
		this.tipoDeVehiculo = tipoDeVehiculo;
		return this;
	}

	/**
	 * @param placa the placa to set
	 * @return VehiculoTestDataBuilder
	 */
	public VehiculoTestDataBuilder conPlaca(String placa) {
		this.placa = placa;
		return this;
	}

	/**
	 * @param cilindraje the cilindraje to set
	 * @return VehiculoTestDataBuilder
	 */
	public VehiculoTestDataBuilder conCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
		return this;
	}
	
	/**
	 * @return the Vehiculo
	 */
	public Vehiculo buildVehiculo() {
		return new Vehiculo(this.tipoDeVehiculo, this.placa, this.cilindraje);
	}
	
	
	
	

}
