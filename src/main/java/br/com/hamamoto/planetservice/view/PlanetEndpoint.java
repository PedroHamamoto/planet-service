package br.com.hamamoto.planetservice.view;

import br.com.hamamoto.planetservice.domain.Planet;
import br.com.hamamoto.planetservice.infrastructure.converter.PlanetConverter;
import br.com.hamamoto.planetservice.service.PlanetService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/rs/planets")
public class PlanetEndpoint {

    private final PlanetService service;
    private final PlanetConverter converter;

    public PlanetEndpoint(PlanetService service, PlanetConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public HttpEntity<PlanetResource> create(@RequestBody @Valid PlanetCreationRequest request) {
        Planet savedPlanet = service.create(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(savedPlanet.getId()).build().toUri();

        return ResponseEntity.created(uri).body(converter.toResource(savedPlanet));
    }

    @GetMapping
    public HttpEntity<List<PlanetResource>> getAll() {
        List<Planet> planets = service.findAll();

        List<PlanetResource> planetResources =
                planets.stream()
                        .map(planet -> converter.toResource(planet))
                        .collect(Collectors.toList());

        return ResponseEntity.ok().body(planetResources);
    }

    @GetMapping("/{id}")
    public HttpEntity<PlanetResource> getOne(@PathVariable("id") String id) {
        Planet planet = service.findById(id);

        return ResponseEntity.ok(converter.toResource(planet));
    }

}
