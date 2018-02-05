package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dominio.TipoDeVehiculo;

@SpringBootApplication
public class ParkingProjectApplication {	
	
	public static void main(String[] args) {
		SpringApplication.run(ParkingProjectApplication.class, args);
		
		TipoDeVehiculo tipo = Enum.valueOf(TipoDeVehiculo.class, "CARRO");
		
		System.out.println(tipo.toString());
		
	}
}
