package com.example.josluis.impromusic.Tablas;

/**
 * Created by jlram on 09/04/2018.
 */

public class Musician {

    private int ID;
    private String username;
    private String password;
    private String log_date;
    private String user_type;
    private int id_pic;

    public Musician(int ID, String username, String password, String log_date, String user_type, int id_pic) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.log_date = log_date;
        this.user_type = user_type;
        this.id_pic = id_pic;
    }

    public Musician() {
        this.ID = 2;
        this.username = "invitado";
        this.password = "invitado";
        this.log_date = "log_date";
        this.user_type = "invitado";
        this.id_pic = 1;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLog_date() {
        return log_date;
    }

    public void setLog_date(String log_date) {
        this.log_date = log_date;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public int getId_pic() {
        return id_pic;
    }

    public void setId_pic(int id_pic) {
        this.id_pic = id_pic;
    }
}
