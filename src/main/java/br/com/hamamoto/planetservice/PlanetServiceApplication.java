package br.com.hamamoto.planetservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PlanetServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanetServiceApplication.class, args);
	}

}
