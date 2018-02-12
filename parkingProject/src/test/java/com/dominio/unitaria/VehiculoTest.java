/**
 * 
 */
package com.dominio.unitaria;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.dominio.TipoDeVehiculo;
import com.dominio.Vehiculo;
import com.dominio.testdatabuilder.VehiculoTestDataBuilder;

/**
 * @author luis.cortes
 *
 */
public class VehiculoTest {
	
	private final TipoDeVehiculo TIPODEVEHICULO = TipoDeVehiculo.MOTO;
	private final String PLACA = "MVA-18D";
	private final int CILINDRAJE = 100;

	@Test
	public void construirVehiculo() {
		//Arrange
		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conCilindraje(CILINDRAJE).
				conPlaca(PLACA).
				conTipoDeVehiculo(TIPODEVEHICULO);
		
		//Act 
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo();
		
		//Assert
		assertEquals(CILINDRAJE, vehiculo.getCilindraje());
		assertEquals(PLACA, vehiculo.getPlaca());
		assertEquals(TIPODEVEHICULO, vehiculo.getTipoDeVehiculo());		
	}
	
	@Test
	public void modificarPlaca() {
		// Arrange
		Vehiculo vehiculo = new Vehiculo();
		String placa = "LIV-77D";
		
		// Act
		vehiculo.setPlaca(placa);
		
		// Assert
		Assert.assertEquals(placa, vehiculo.getPlaca());
		
	}
	
	@Test
	public void modificarTipoDeVehiculo() {
		// Arrange
		Vehiculo vehiculo = new Vehiculo();
		TipoDeVehiculo tipoDeVehiculo = TipoDeVehiculo.CARRO;
		
		// Act
		vehiculo.setTipoDeVehiculo(tipoDeVehiculo);
		
		// Assert
		Assert.assertEquals(tipoDeVehiculo, vehiculo.getTipoDeVehiculo());
	}
	
	@Test
	public void modificarCilindraje() {
		// Arrange
		Vehiculo vehiculo = new Vehiculo();
		int cilindraje = 150;
		
		// Act
		vehiculo.setCilindraje(cilindraje);
		
		// Assert
		Assert.assertEquals(cilindraje, vehiculo.getCilindraje());
	}

}
