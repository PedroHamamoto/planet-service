package br.com.hamamoto.planetservice.view;

import br.com.hamamoto.planetservice.BaseTest;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class PlanetEndpointTest extends BaseTest {

    @Test
    public void shouldCreateAPlanet() throws Exception {
        stubFor(get(urlEqualTo("/planets?search=Tatooine"))
            .willReturn(aResponse()
                .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile("swapi/planets/responses/tatooine-planet-search.json")));

        given()
            .log().everything()
            .body(resource("fixtures/planets/requests/new-planet.json"))
            .headers(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .when()
            .post(address() + "planets")
            .then().log().everything()
        .assertThat()
            .statusCode(HttpStatus.SC_CREATED)
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
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}