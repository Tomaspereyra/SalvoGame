package com.codeoftheweb.salvo.model;

import javax.persistence.*;

@Embeddable
public class ShipLocations {

    private String cell;

    public ShipLocations(String cell) {
        this.cell = cell;
    }
    public ShipLocations() {

    }



    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    @Override
    public String toString() {
        return "ShipLocations{" +
                "cell='" + cell + '\'' +
                '}';
    }
}
