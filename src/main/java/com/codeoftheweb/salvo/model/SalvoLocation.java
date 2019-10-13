package com.codeoftheweb.salvo.model;

import javax.persistence.*;

@Entity
public class SalvoLocation {
    @ManyToOne(fetch = FetchType.EAGER)
    private Salvo salvo;
    @Column
    private String cell;

    public SalvoLocation(Salvo salvo, String cell) {
        this.salvo = salvo;
        this.cell = cell;
    }
    public SalvoLocation() {
    }

    public Salvo getSalvo() {
        return salvo;
    }

    public void setSalvo(Salvo salvo) {
        this.salvo = salvo;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    @Override
    public String toString() {
        return "SalvoLocation{" +
                "salvo=" + salvo +
                ", cell='" + cell + '\'' +
                '}';
    }
}
