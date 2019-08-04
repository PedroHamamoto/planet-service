package br.com.hamamoto.planetservice.view;

import br.com.hamamoto.planetservice.BaseTest;
import br.com.hamamoto.planetservice.domain.Planet;
import org.junit.Test;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static wiremock.org.apache.http.HttpStatus.SC_NO_CONTENT;

public class PlanetEndpointDeleteTest extends BaseTest {

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
