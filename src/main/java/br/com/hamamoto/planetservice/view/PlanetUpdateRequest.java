package br.com.hamamoto.planetservice.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class PlanetUpdateRequest {

    @JsonIgnore
    private String id;

    @NotEmpty(message = "name cannot be empty")
    private String name;
    @NotEmpty(message = "climate cannot be empty")
    private String climate;
    @NotEmpty(message = "terrain cannot be empty")
    private String terrain;

    @Min(value = 0, message = "appearances-quantity cannot be below 0")
    @JsonProperty("appearances-quantity")
    private Integer appearancesQuantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Integer getAppearancesQuantity() {
        return appearancesQuantity;
    }

    public void setAppearancesQuantity(Integer appearancesQuantity) {
        this.appearancesQuantity = appearancesQuantity;
    }
}
