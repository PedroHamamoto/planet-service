package br.com.hamamoto.planetservice.view.resource;

import javax.validation.constraints.NotEmpty;

public class PlanetCreationRequest {

    @NotEmpty(message = "name cannot be empty")
    private String name;
    @NotEmpty(message = "climate cannot be empty")
    private String climate;
    @NotEmpty(message = "terrain cannot be empty")
    private String terrain;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }
}
