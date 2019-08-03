package br.com.hamamoto.planetservice.service;

import br.com.hamamoto.planetservice.domain.Planet;
import br.com.hamamoto.planetservice.infrastructure.converter.PlanetConverter;
import br.com.hamamoto.planetservice.integration.SwapiPlanetGateway;
import br.com.hamamoto.planetservice.integration.resource.SwapiPlanetResource;
import br.com.hamamoto.planetservice.integration.resource.SwapiSearcResult;
import br.com.hamamoto.planetservice.view.PlanetCreationRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlanetService {

    private final PlanetConverter converter;
    private final PlanetRepository repository;
    private final SwapiPlanetGateway gateway;

    public PlanetService(PlanetConverter converter, PlanetRepository repository, SwapiPlanetGateway gateway) {
        this.converter = converter;
        this.repository = repository;
        this.gateway = gateway;
    }

    public Planet create(PlanetCreationRequest request) {
        Planet planet = converter.toEntity(request);
        SwapiSearcResult<SwapiPlanetResource> planets = gateway.findPlanetsByName(request.getName());

        Optional<SwapiPlanetResource> first =
                planets.getResults().stream()
                        .filter(swapiPlanet -> swapiPlanet.getName().equals(request.getName())).findFirst();

        planet.setAppearancesQuantity(first.get().getFilms().size());

        return repository.save(planet);
    }

}
