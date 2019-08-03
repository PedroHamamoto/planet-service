package br.com.hamamoto.planetservice;

import br.com.hamamoto.planetservice.domain.Planet;
import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWireMock(port = 9195)
@ActiveProfiles("test")
public abstract class BaseTest {

    private final String RESOURCE_DIRECTORY = "src/test/resources/";

    @LocalServerPort
    private int port;

    @Autowired
    private MongoTemplate mongoTemplate;

    @After
    public void tearDown() throws Exception {
        mongoTemplate.dropCollection(Planet.class);
    }

    public String address() {
        return String.format("http://localhost:%d/rs/", port);
    }

    public String resource(String path) throws IOException {
        File file = new File(RESOURCE_DIRECTORY + path);

        if (!file.exists()) {
            throw new IllegalArgumentException("Payload file not found: " + RESOURCE_DIRECTORY + path);
        }

        return new String(Files.readAllBytes(Paths.get(file.getPath())));
    }

    public void insertPlanet(String path) throws IOException {
        mongoTemplate.insert(Document.parse(resource(path)), "planets");
    }

    public Planet findById(String id) {
        return mongoTemplate.findById(id, Planet.class);
    }
}
