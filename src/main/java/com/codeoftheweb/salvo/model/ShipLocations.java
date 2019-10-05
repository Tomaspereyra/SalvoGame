package com.codeoftheweb.salvo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ShipLocations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shipID;
    private String cell;

    public ShipLocations(Integer shipID, String cell) {
        this.shipID = shipID;
        this.cell = cell;
    }

    public Integer getShipID() {
        return shipID;
    }

    public void setShipID(Integer shipID) {
        this.shipID = shipID;
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
                "shipID=" + shipID +
                ", cell='" + cell + '\'' +
                '}';
    }
}
