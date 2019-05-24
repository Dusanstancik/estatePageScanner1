/*
 * Copyright (c) 2019. Dušan Stančík
 */

package Models;

public class ModelLokalita {
    Integer id;
    String nazov;

    public ModelLokalita(Integer id, String nazov) {
        this.id = id;
        this.nazov = nazov;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }
}
