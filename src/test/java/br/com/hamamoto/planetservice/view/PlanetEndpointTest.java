package br.com.hamamoto.planetservice.view;

import br.com.hamamoto.planetservice.BaseTest;
import br.com.hamamoto.planetservice.domain.Planet;
import org.junit.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static wiremock.org.apache.http.HttpStatus.*;

public class PlanetEndpointTest extends BaseTest {

    @Test
    public void shouldCreateAPlanet() throws Exception {
        stubFor(get(urlEqualTo("/planets?search=Tatooine"))
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
    public void shouldGeASinglePlanet() throws IOException {
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

    @Test
    public void shouldUpdatePlanet() throws IOException {
        insertPlanet("fixtures/planets/data/bespin.json");

        given()
            .log().everything()
            .body(resource("fixtures/planets/requests/update-planet.json"))
            .headers(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .when()
            .put(address() + "planets/5c633aaa0db0365b7d0e000f")
        .then().log().everything()
            .assertThat()
            .statusCode(SC_NO_CONTENT);

        Planet planet = findById("5c633aaa0db0365b7d0e000f");

        assertThat(planet.getId(), is("5c633aaa0db0365b7d0e000f"));
        assertThat(planet.getName(), is("Geonosis"));
        assertThat(planet.getClimate(), is("temperate, arid"));
        assertThat(planet.getTerrain(), is("rock, desert, mountain, barren"));
        assertThat(planet.getAppearancesQuantity(), is(1));
    }

    @Test
    public void shouldDeletePlanet() throws IOException {
        insertPlanet("fixtures/planets/data/bespin.json");

        given()
            .log().everything()
        .when()
            .delete(address() + "planets/5c633aaa0db0365b7d0e000f")
        .then().log().everything()
            .assertThat()
            .statusCode(SC_NO_CONTENT);

        Planet planet = findById("5c633aaa0db0365b7d0e000f");

        assertThat(planet, is(nullValue()));
    }
}