/**
 * 
 */
package dominio.integracion;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
import com.dominio.exception.ParkingException;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import testdatabuilder.VehiculoTestDataBuilder;

/**
 * @author luis.cortes
 *
 */
@RunWith(JUnitParamsRunner.class)
public class ParkingServicesTest {
	
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
	
	// Vehiculos con diferente fecha de ingreso y salida
	private Object[] parametersToTestObtenerVehiculosParqueados(){
		return new Object[] {
			new Object[] {new RegistroDeIngreso(new VehiculoTestDataBuilder().conCilindraje(500).buildVehiculo(),
												LocalDateTime.of(2018, 1, 29, 10, 0))},
			new Object[] {new RegistroDeIngreso(new VehiculoTestDataBuilder().conCilindraje(500).buildVehiculo(),
										    	LocalDateTime.of(2018, 1, 29, 10, 0))}
		};
	}

	@Test
	@Parameters(method = "parametersToTestObtenerVehiculosParqueados")
	public void obtenerVehiculosParqueados(RegistroDeIngreso registroDeIngreso) {
		// Arrange
		List<RegistroDeIngreso> registroDeIngresos; 
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, parkingServices);
		celadorParqueadero.atenderSolicitudDeIngreso(registroDeIngreso.getVehiculo(), registroDeIngreso.getFechaDeIngreso()); 
		
		// Act
		registroDeIngresos = parkingServices.obtenerVehiculosParqueados();
		
		// Assert
		for (RegistroDeIngreso reg : registroDeIngresos) {
			
			Assert.assertEquals(registroDeIngreso.getVehiculo().getPlaca(), reg.getVehiculo().getPlaca());
			Assert.assertEquals(registroDeIngreso.getVehiculo().getCilindraje(), reg.getVehiculo().getCilindraje());
			Assert.assertEquals(registroDeIngreso.getVehiculo().getTipoDeVehiculo(), reg.getVehiculo().getTipoDeVehiculo());
			Assert.assertEquals(registroDeIngreso.getFechaDeIngreso(), reg.getFechaDeIngreso());
		}
	}
	
	// Vehiculos con diferente fecha de ingreso y salida
	private Object[] parametersToTestRegistrarSalidaVehiculoNoParqueado(){
		return new Object[] {
			new Object[] {new RegistroDeIngreso(new VehiculoTestDataBuilder().conCilindraje(500).buildVehiculo(),
												LocalDateTime.of(2018, 1, 29, 10, 0))}
		};
	}
	
	@Test
	@Parameters(method = "parametersToTestRegistrarSalidaVehiculoNoParqueado")
	public void registrarSalidaVehiculoNoParqueado(RegistroDeIngreso registroDeIngreso) {
		// Arrange
		ParkingServices parkingServices = mock(ParkingServices.class);
		CeladorParqueadero celadorParqueadero = new CeladorParqueadero(parqueadero, parkingServices);		
		
		when(parkingServices.obtenerRegistroDeIngresoPorPlaca(registroDeIngreso.getVehiculo().getPlaca())).thenReturn(null);		
		
		// Act		
		try {
			
			celadorParqueadero.atenderSalidaDelVehiculo(registroDeIngreso.getVehiculo().getPlaca(), LocalDateTime.now());
			
		} catch (ParkingException e) {
			// Assert
			Assert.assertEquals(CeladorParqueadero.EL_VEHICULO_NO_SE_ENCUENTRA_PARQUEADO, e.getMessage());
		}		
	}
	
	// Vehiculos con diferente fecha de ingreso y salida
		private Object[] parametersToTestRegistrarSalidaVehiculo(){
			return new Object[] {
				new Object[] {new RegistroDeIngreso(new VehiculoTestDataBuilder().conCilindraje(500).buildVehiculo(),
													LocalDateTime.of(2018, 1, 29, 10, 0))}
			};
		}
		
		@Test
		@Parameters(method = "parametersToTestRegistrarSalidaVehiculo")
		public void registrarSalidaVehiculo(RegistroDeIngreso registroDeIngreso) {
			// Arrange
			CeladorParqueadero celadorParqueadero = new CeladorParqueadero(this.parqueadero, this.parkingServices);		
			celadorParqueadero.atenderSolicitudDeIngreso(registroDeIngreso.getVehiculo(), registroDeIngreso.getFechaDeIngreso());
			
			// Act					
			celadorParqueadero.atenderSalidaDelVehiculo(registroDeIngreso.getVehiculo().getPlaca(), LocalDateTime.now());
			
			// Assert 
			Assert.assertNull(this.parkingServices.obtenerRegistroDeIngresoPorPlaca(registroDeIngreso.getVehiculo().getPlaca())); 
			
		}
	
}
