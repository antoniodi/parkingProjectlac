package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dao.services.ParkingServices;
import com.dao.services.ParkingServicesImpl;
import com.dominio.TipoDeVehiculo;

@SpringBootApplication
public class ParkingProjectApplication {	
	
	public static void main(String[] args) {
		SpringApplication.run(ParkingProjectApplication.class, args);
	}
}
