/**
 * 
 */
package dominio.unitaria;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.dominio.TipoDeVehiculo;
import com.dominio.Vehiculo;

import testdatabuilder.VehiculoTestDataBuilder;

/**
 * @author luis.cortes
 *
 */
public class VehiculoTest {
	
	private final TipoDeVehiculo TIPODEVEHICULO = TipoDeVehiculo.MOTO;
	private final String PLACA = "MVA-18D";
	private final int CILINDRAJE = 100;

	@Test
	public void test() {
		//Arrange
		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conCilindraje(CILINDRAJE).
				conPlaca(PLACA).
				conTipoDeVehiculo(TIPODEVEHICULO);
		
		//Act 
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo();
		
		//Arrange
		assertEquals(CILINDRAJE, vehiculo.getCilindraje());
		assertEquals(PLACA, vehiculo.getPlaca());
		assertEquals(TIPODEVEHICULO, vehiculo.getTipoDeVehiculo());		
	}

}
