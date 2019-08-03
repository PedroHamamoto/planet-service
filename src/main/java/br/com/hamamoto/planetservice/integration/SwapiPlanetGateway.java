package br.com.hamamoto.planetservice.integration;

import br.com.hamamoto.planetservice.integration.resource.SwapiPlanetResource;
import br.com.hamamoto.planetservice.integration.resource.SwapiSearcResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.GET;

@Component
public class SwapiPlanetGateway {

    public static final String PLANETS_SEARCH_NAME = "/planets?search=%s";
    public static final HttpEntity<?> EMPTY_BODY = null;
    private final RestTemplate template;
    private final String swapiUrl;

    public SwapiPlanetGateway(RestTemplate template, @Value("${swapi.url}") String swapiUrl) {
        this.template = template;
        this.swapiUrl = swapiUrl;
    }

    public SwapiSearcResult<SwapiPlanetResource> findPlanetsByName(String name) {
        String url = String.format(swapiUrl + PLANETS_SEARCH_NAME, name);

        ResponseEntity<SwapiSearcResult<SwapiPlanetResource>> response = template.exchange(url,
                GET,
                EMPTY_BODY,
                new ParameterizedTypeReference<SwapiSearcResult<SwapiPlanetResource>>() {
                });

        return response.getBody();


    }

}