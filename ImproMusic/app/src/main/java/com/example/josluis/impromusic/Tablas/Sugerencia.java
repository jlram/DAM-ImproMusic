package com.example.josluis.impromusic.Tablas;

public class Sugerencia {

    int ID;
    int ID_musician;
    String content;

    public Sugerencia(int ID, int ID_musician, String content) {
        this.ID = ID;
        this.ID_musician = ID_musician;
        this.content = content;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_musician() {
        return ID_musician;
    }

    public void setID_musician(int ID_musician) {
        this.ID_musician = ID_musician;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
