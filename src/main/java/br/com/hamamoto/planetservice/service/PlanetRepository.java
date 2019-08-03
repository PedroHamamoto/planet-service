package br.com.hamamoto.planetservice.service;

import br.com.hamamoto.planetservice.domain.Planet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlanetRepository extends MongoRepository<Planet, String> {
}
