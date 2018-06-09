package com.example.josluis.impromusic.Tablas;

public class Sugerencia {

    int ID;
    String username;
    String content;

    public Sugerencia(int ID, String username, String content) {
        this.ID = ID;
        this.username = username;
        this.content = content;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
