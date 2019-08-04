package br.com.hamamoto.planetservice.integration;

import br.com.hamamoto.planetservice.domain.Planet;
import br.com.hamamoto.planetservice.integration.resource.SwapiPlanetResource;
import br.com.hamamoto.planetservice.integration.resource.SwapiSearcResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
public class SwapiPlanetGateway {

    public static final String PLANETS_SEARCH_NAME = "/planets?search=%s";
    public static final String ALL_PLANETS = "planets";

    public static final HttpEntity<?> EMPTY_BODY = null;
    private final RestTemplate template;
    private final String swapiUrl;

    public SwapiPlanetGateway(RestTemplate template, @Value("${swapi.url}") String swapiUrl) {
        this.template = template;
        this.swapiUrl = swapiUrl;
    }

    public SwapiSearcResult<SwapiPlanetResource> findPlanetsByName(String name) {
        String url = String.format(swapiUrl + PLANETS_SEARCH_NAME, name);

        ResponseEntity<SwapiSearcResult<SwapiPlanetResource>> response = getSwapiSearchResource(url);

        return response.getBody();
    }

    @Cacheable("planets")
    public List<SwapiPlanetResource> findAll() {
        String url = swapiUrl + ALL_PLANETS;
        ArrayList<SwapiPlanetResource> swapiPlanetResources = new ArrayList<>();

        do {
            ResponseEntity<SwapiSearcResult<SwapiPlanetResource>> response = getSwapiSearchResource(url);
            swapiPlanetResources.addAll(response.getBody().getResults());

            url = response.getBody().getNext();
        } while(url != null);

        return swapiPlanetResources;
    }

    private ResponseEntity<SwapiSearcResult<SwapiPlanetResource>> getSwapiSearchResource(String url) {
        return template.exchange(url,
                GET,
                EMPTY_BODY,
                new ParameterizedTypeReference<SwapiSearcResult<SwapiPlanetResource>>() {
                });
    }
}
