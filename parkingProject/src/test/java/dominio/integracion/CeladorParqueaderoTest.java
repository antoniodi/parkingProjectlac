package dominio.integracion;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.dao.DAOHistoricoDeParqueoImpl;
import com.dao.interfaces.DAOHistoricoDeParqueo;
import com.dao.services.ParkingServices;
import com.dao.services.ParkingServicesImpl;
import com.dominio.CeladorParqueadero;
import com.dominio.Parqueadero;
import com.dominio.RegistroDeIngreso;
import com.dominio.Tarifa;
import com.dominio.TicketDePago;
import com.dominio.TipoDeVehiculo;
import com.dominio.Vehiculo;
import com.dominio.exception.ParkingException;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import testdatabuilder.VehiculoTestDataBuilder;

@RunWith(JUnitParamsRunner.class)
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
	public void noHayCuposDisponiblesMoto() {
		// Arrange				
		LocalDateTime fechaIngresoMartes = LocalDateTime.now();
		
		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conPlaca("LIV-26A").
				conTipoDeVehiculo(TipoDeVehiculo.MOTO);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo();	
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(this.parqueadero, this.parkingServices);	
		
		for (int i = 0; i < 9; i++) {
			celadorParqueadero.atenderSolicitudDeIngreso(new VehiculoTestDataBuilder().
														 conPlaca("LIV-"+i+"A").
														 buildVehiculo(), 
														 fechaIngresoMartes);
		}
		
		// Act
		try {
		
			celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);	
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(CeladorParqueadero.NO_HAY_CUPOS_DISPONIBLES, e.getMessage());
		}				
	}
	
	@Test
	public void noHayCuposDisponiblesCarro() {
		// Arrange				
		LocalDateTime fechaIngresoMartes = LocalDateTime.now();
		
		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				conTipoDeVehiculo(TipoDeVehiculo.CARRO).
				conPlaca("LIV-26A");
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.buildVehiculo();	
				
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(this.parqueadero, this.parkingServices);	
		
		for (int i = 0; i < 19; i++) {
			celadorParqueadero.atenderSolicitudDeIngreso(new VehiculoTestDataBuilder().
														 conPlaca("LIV-"+i).
														 conTipoDeVehiculo(TipoDeVehiculo.CARRO).
														 buildVehiculo(), 
														 fechaIngresoMartes);
		}
		
		// Act
		try {
		
			celadorParqueadero.atenderSolicitudDeIngreso(vehiculo, fechaIngresoMartes);	
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(CeladorParqueadero.NO_HAY_CUPOS_DISPONIBLES, e.getMessage());
		}				
	}
	
	// Vehiculos con diferente fecha de ingreso y salida
		private Object[] parametersToTestGenerarTicketDePago(){
			return new Object[] {
				new Object[] {new RegistroDeIngreso(new VehiculoTestDataBuilder().conCilindraje(500).buildVehiculo(),
													LocalDateTime.of(2018, 1, 29, 10, 0)),
							 LocalDateTime.of(2018, 1, 29, 10, 59),
							 new BigDecimal("500.0000")},
				new Object[] {new RegistroDeIngreso(new VehiculoTestDataBuilder().conCilindraje(500).buildVehiculo(),
											    	LocalDateTime.of(2018, 1, 29, 10, 0)),
							 LocalDateTime.of(2018, 1, 29, 11, 1),
							 new BigDecimal("1000.0000")},
				new Object[] {new RegistroDeIngreso(new VehiculoTestDataBuilder().conCilindraje(650).buildVehiculo(),
							  						LocalDateTime.of(2018, 1, 29, 10, 0)),
							 LocalDateTime.of(2018, 1, 29, 20, 59),
							 new BigDecimal("6000.0000")},
				new Object[] {new RegistroDeIngreso(new VehiculoTestDataBuilder().conCilindraje(501).buildVehiculo(),
													LocalDateTime.of(2018, 1, 29, 10, 0)),
							 LocalDateTime.of(2018, 1, 29, 11, 1),
							 new BigDecimal("3000.0000")},
				new Object[] {new RegistroDeIngreso(new VehiculoTestDataBuilder().conTipoDeVehiculo(TipoDeVehiculo.CARRO).conCilindraje(500).buildVehiculo(),
							  						LocalDateTime.of(2018, 1, 25, 10, 0)),
						     LocalDateTime.of(2018, 1, 26, 12, 59),
						     new BigDecimal("11000.0000")},
				new Object[] {new RegistroDeIngreso(new VehiculoTestDataBuilder().conTipoDeVehiculo(TipoDeVehiculo.CARRO).conCilindraje(501).buildVehiculo(),
													LocalDateTime.of(2018, 1, 25, 10, 0)),
							 LocalDateTime.of(2018, 1, 25, 11, 1),
							 new BigDecimal("2000.0000")}
			};
		}

		@Test
		@Parameters(method = "parametersToTestGenerarTicketDePago")
		public void generarTicketDePago(RegistroDeIngreso registroDeIngreso, LocalDateTime fechaSalida, BigDecimal expectedValue) {
			
			// Arrange		
			CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, parkingServices);			
					
			// Act
			TicketDePago ticketDePago = celadorParqueadero.generarTicketDePago(registroDeIngreso, fechaSalida);
			
			// Assert
			Assert.assertEquals(expectedValue, ticketDePago.getTotal());
								
		}

}
