package com.codeoftheweb.salvo.model;

import javax.persistence.*;

@Embeddable
public class ShipLocations {

    private String cell;

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
