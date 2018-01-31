package dominio;

import java.time.LocalDateTime;

public class RegistroDeIngreso {

	private Vehiculo vehiculo;
	private LocalDateTime fechaDeIngresio;
	
	
	
	/**
	 * @param vehiculo
	 * @param fechaDeIngresio
	 */
	public RegistroDeIngreso(Vehiculo vehiculo, LocalDateTime fechaDeIngresio) {		
		this.vehiculo = vehiculo;
		this.fechaDeIngresio = fechaDeIngresio;
	}
	
	/**
	 * @return the vehiculo
	 */
	public Vehiculo getVehiculo() {
		return vehiculo;
	}
	/**
	 * @return the fechaDeIngresio
	 */
	public LocalDateTime getFechaDeIngresio() {
		return fechaDeIngresio;
	}
	
	

}
