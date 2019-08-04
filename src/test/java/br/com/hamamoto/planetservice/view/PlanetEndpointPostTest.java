package br.com.hamamoto.planetservice.view;

import br.com.hamamoto.planetservice.BaseTest;
import org.junit.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static wiremock.org.apache.http.HttpStatus.*;

public class PlanetEndpointPostTest extends BaseTest {

    @Test
    public void shouldCreateAPlanet() throws Exception {
        stubFor(get(urlEqualTo("/api/planets?search=Tatooine"))
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .withStatus(SC_OK)
                .withBodyFile("swapi/planets/responses/tatooine-planet-search.json")));

        given()
            .log().everything()
            .body(resource("fixtures/planets/requests/new-planet.json"))
            .headers(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .when()
            .post(address() + "planets")
        .then().log().everything()
            .assertThat()
            .statusCode(SC_CREATED)
            .body("id", notNullValue())
            .body("name", is("Tatooine"))
            .body("climate", is("arid"))
            .body("terrain", is("desert"))
            .body("appearances-quantity", is(5));
    }

    @Test
    public void shouldReturnBadRequestWhenParametersAreEmpty()throws Exception {
        given()
            .log().everything()
            .body(resource("fixtures/planets/requests/invalid-planet.json"))
            .headers(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .when()
            .post(address() + "planets")
        .then().log().everything()
            .assertThat()
            .statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void shouldReturnConflictWhenPlanetIsRegistered() throws IOException {
        insertPlanet("fixtures/planets/data/tatooine.json");

        given()
            .log().everything()
            .body(resource("fixtures/planets/requests/new-planet.json"))
            .headers(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .when()
            .post(address() + "planets")
        .then().log().everything()
            .assertThat()
            .statusCode(SC_CONFLICT);
    }

    @Test
    public void shouldReturnBadRequestWhenItsNotAStarWarsPlanet() throws IOException {
        stubFor(get(urlEqualTo("/api/planets?search=Namekuzen"))
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .withStatus(SC_OK)
                .withBodyFile("swapi/planets/responses/not-found-planet-search.json")));

        given()
            .log().everything()
            .body(resource("fixtures/planets/requests/invalid-new-planet.json"))
            .headers(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .when()
            .post(address() + "planets")
        .then().log().everything()
            .assertThat()
            .statusCode(SC_BAD_REQUEST);
    }

}
