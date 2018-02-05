package dominio.integracion;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.dao.DAOHistoricoDeParqueoImpl;
import com.dao.interfaces.DAOHistoricoDeParqueo;
import com.dao.services.ParkingServices;
import com.dao.services.ParkingServicesImpl;
import com.dominio.CeladorParqueadero;
import com.dominio.Parqueadero;
import com.dominio.TipoDeVehiculo;
import com.dominio.Vehiculo;
import com.dominio.exception.ParkingException;

import testdatabuilder.VehiculoTestDataBuilder;

public class CeladorParqueaderoTest {
	
	private ParkingServices parkingServices;
	private Parqueadero parqueadero;
	private DAOHistoricoDeParqueo daoHistoricoDeParqueo;
	
	@Before
	public void setUp() {
		
		this.parkingServices = new ParkingServicesImpl();
		this.parqueadero = new Parqueadero();
		this.daoHistoricoDeParqueo = new DAOHistoricoDeParqueoImpl();
		
	}
	
	@After
	public void tearDown() {
		
		daoHistoricoDeParqueo.eliminarHistoricoDeParqueo(); 

	}
	
	@Test
	public void vehiculoSeEncuentraParqueado() {
		// Arrange
		LocalDateTime fechaIngresoMartes = LocalDateTime.now();
		
		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conPlaca("LIV-260").
				conTipoDeVehiculo(TipoDeVehiculo.CARRO);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo();	
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(this.parqueadero, this.parkingServices);
		
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		
		try {
			// Act
			celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
			fail();
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(CeladorParqueadero.EL_VEHICULO_YA_SE_ENCUENTRA_ESTACIONADO, e.getMessage());
		}				
	}
	
	@Test
	public void ingresarVehiculoNoSeEncuentraParqueado() {
		// Arrange
		LocalDateTime fechaIngresoMartes = LocalDateTime.now();
		
		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conPlaca("LIV-260").
				conTipoDeVehiculo(TipoDeVehiculo.CARRO);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo();	
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(this.parqueadero, this.parkingServices);		
		
		// Act
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		
		// Assert
		Assert.assertEquals(vehiculo.getPlaca(), parkingServices.obtenerRegistroDeIngresoPorPlaca(vehiculo.getPlaca()).getVehiculo().getPlaca());
		Assert.assertEquals(vehiculo.getCilindraje(), parkingServices.obtenerRegistroDeIngresoPorPlaca(vehiculo.getPlaca()).getVehiculo().getCilindraje());
		Assert.assertEquals(vehiculo.getTipoDeVehiculo(), parkingServices.obtenerRegistroDeIngresoPorPlaca(vehiculo.getPlaca()).getVehiculo().getTipoDeVehiculo());
	}
	
	@Test
	public void noHayCuposDisponibles() {
		// Arrange				
		LocalDateTime fechaIngresoMartes = LocalDateTime.now();
		
		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conPlaca("LIV-260").
				conTipoDeVehiculo(TipoDeVehiculo.CARRO);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo();	
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(this.parqueadero, this.parkingServices);	
		
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		
		// Act
		try {
		
			celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);	
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(CeladorParqueadero.NO_HAY_CUPOS_DISPONIBLES, e.getMessage());
		}				
	}
	
	@Test
	public void vehiculoSeEncuentraParqueado() {
		// Arrange
		LocalDateTime fechaIngresoMartes = LocalDateTime.now();
		
		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conPlaca("LIV-260").
				conTipoDeVehiculo(TipoDeVehiculo.CARRO);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo();	
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(this.parqueadero, this.parkingServices);
		
		celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
		
		try {
			// Act
			celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);
			fail();
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(CeladorParqueadero.EL_VEHICULO_YA_SE_ENCUENTRA_ESTACIONADO, e.getMessage());
		}				
	}

}
