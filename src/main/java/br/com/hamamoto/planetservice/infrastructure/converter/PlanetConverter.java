package br.com.hamamoto.planetservice.infrastructure.converter;

import br.com.hamamoto.planetservice.domain.Planet;
import br.com.hamamoto.planetservice.integration.resource.SwapiPlanetResource;
import br.com.hamamoto.planetservice.view.PlanetCreationRequest;
import br.com.hamamoto.planetservice.view.PlanetResource;
import br.com.hamamoto.planetservice.view.PlanetUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlanetConverter {

    private final ModelMapper mapper;

    public PlanetConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Planet toEntity(PlanetCreationRequest request) {
        return mapper.map(request, Planet.class);
    }

    public Planet toEntity(PlanetUpdateRequest request) {
        return mapper.map(request, Planet.class);
    }

    public Planet toEntity(SwapiPlanetResource resource) {
        Planet planet = mapper.map(resource, Planet.class);
        planet.setAppearancesQuantity(resource.getFilms().size());

        return planet;
    }

    public PlanetResource toResource(Planet planet) {
        return mapper.map(planet, PlanetResource.class);
    }

}
