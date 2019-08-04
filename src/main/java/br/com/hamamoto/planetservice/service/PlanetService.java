package br.com.hamamoto.planetservice.service;

import br.com.hamamoto.planetservice.domain.Planet;
import br.com.hamamoto.planetservice.infrastructure.converter.PlanetConverter;
import br.com.hamamoto.planetservice.infrastructure.exception.domain.ApplicationException;
import br.com.hamamoto.planetservice.infrastructure.exception.domain.Message;
import br.com.hamamoto.planetservice.integration.SwapiPlanetGateway;
import br.com.hamamoto.planetservice.integration.resource.SwapiPlanetResource;
import br.com.hamamoto.planetservice.integration.resource.SwapiSearcResult;
import br.com.hamamoto.planetservice.view.PlanetCreationRequest;
import br.com.hamamoto.planetservice.view.PlanetUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Planet> findAll(String origin) {
        if ("database".equals(origin)) {
            return repository.findAll();
        } else if ("swapi".equals(origin)) {
            return gateway.findAll().stream()
                .map(planet -> converter.toEntity(planet))
                .collect(Collectors.toList());
        }

        throw new ApplicationException(Message.UNKNOWN_ORIGIN);
    }

    public Planet findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ApplicationException(Message.PLANET_NOT_FOUND));
    }

    public void update(PlanetUpdateRequest request) {
        findById(request.getId());

        Planet planet = converter.toEntity(request);

        repository.save(planet);

    }

    public void delete(String id) {
        Planet planet = findById(id);

        repository.delete(planet);
    }

    public Planet findByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new ApplicationException(Message.PLANET_NOT_FOUND));
    }
}
