package br.com.hamamoto.planetservice.view;

import br.com.hamamoto.planetservice.BaseTest;
import br.com.hamamoto.planetservice.domain.Planet;
import org.junit.Test;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static wiremock.org.apache.http.HttpStatus.SC_NO_CONTENT;

public class PlanetEndpointPutTest extends BaseTest {

    @Test
    public void shouldUpdatePlanet() throws IOException {
        insertPlanet("fixtures/planets/data/bespin.json");

        given()
            .log().everything()
            .body(getResouceAsString("fixtures/planets/requests/update-planet.json"))
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

}
