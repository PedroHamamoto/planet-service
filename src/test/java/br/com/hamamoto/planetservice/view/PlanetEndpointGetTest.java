package br.com.hamamoto.planetservice.view;

import br.com.hamamoto.planetservice.BaseTest;
import org.junit.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static wiremock.org.apache.http.HttpStatus.SC_NOT_FOUND;
import static wiremock.org.apache.http.HttpStatus.SC_OK;

public class PlanetEndpointGetTest extends BaseTest {

    @Test
    public void shouldGetAllPlanetsFromDatabase() throws IOException {
        insertPlanet("fixtures/planets/data/tatooine.json");
        insertPlanet("fixtures/planets/data/alderaan.json");

        given()
            .log().everything()
            .headers(ACCEPT, APPLICATION_JSON_VALUE)
        .when()
            .get(address() + "planets")
        .then().log().everything()
            .assertThat()
            .statusCode(SC_OK)
            .body("$", hasSize(2));
    }

    @Test
    public void shouldGetAllPlanetsFromSwapi() {
        stubFor(get(urlEqualTo("/api/planets"))
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .withStatus(SC_OK)
                .withBodyFile("swapi/planets/responses/all-planets-page-1.json")));

        stubFor(get(urlEqualTo("/api/planets/?page=2"))
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .withStatus(SC_OK)
                .withBodyFile("swapi/planets/responses/all-planets-page-2.json")));

        given()
            .log().everything()
            .headers(ACCEPT, APPLICATION_JSON_VALUE)
            .headers("origin", "swapi")
        .when()
            .get(address() + "planets")
        .then().log().everything()
            .assertThat()
            .statusCode(SC_OK)
            .body("$", hasSize(20));
    }

    @Test
    public void shouldGeASinglePlanetById() throws IOException {
        insertPlanet("fixtures/planets/data/bespin.json");

        given()
            .log().everything()
            .headers(ACCEPT, APPLICATION_JSON_VALUE)
        .when()
            .get(address() + "planets/5c633aaa0db0365b7d0e000f")
        .then().log().everything()
            .assertThat()
            .statusCode(SC_OK)
            .body("id", is("5c633aaa0db0365b7d0e000f"))
            .body("name", is("Bespin"))
            .body("climate", is("temperate"))
            .body("terrain", is("gas giant"))
            .body("appearances-quantity", is(1));
    }

    @Test
    public void shouldGetASinglePlanetByName() throws IOException {
        insertPlanet("fixtures/planets/data/bespin.json");

        given()
            .log().everything()
            .headers(ACCEPT, APPLICATION_JSON_VALUE)
        .when()
            .get(address() + "planets/;name=Bespin")
        .then().log().everything()
            .assertThat()
            .statusCode(SC_OK)
            .body("id", is("5c633aaa0db0365b7d0e000f"))
            .body("name", is("Bespin"))
            .body("climate", is("temperate"))
            .body("terrain", is("gas giant"))
            .body("appearances-quantity", is(1));
    }

    @Test
    public void shouldReturnNotFoundWhenPlanetDoesNotExist() {
        given()
            .log().everything()
            .headers(ACCEPT, APPLICATION_JSON_VALUE)
        .when()
            .get(address() + "planets/5c633aaa0db0365b7d0e000f")
        .then().log().everything()
            .assertThat()
            .statusCode(SC_NOT_FOUND);
    }
}
