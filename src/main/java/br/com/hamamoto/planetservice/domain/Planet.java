package br.com.hamamoto.planetservice.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("planets")
public class Planet {

    private String id;
    private String name;
    private String climate;
    private String terrain;

    @Field("appearances-quantity")
    private int appearancesQuantity;

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

    public int getAppearancesQuantity() {
        return appearancesQuantity;
    }

    public void setAppearancesQuantity(int appearancesQuantity) {
        this.appearancesQuantity = appearancesQuantity;
    }
}
