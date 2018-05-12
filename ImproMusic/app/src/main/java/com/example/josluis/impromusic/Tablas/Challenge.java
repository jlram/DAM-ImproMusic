package com.example.josluis.impromusic.Tablas;

import java.util.Date;

public class Challenge {
    int ID;
    String name;
    int ID_song;
    int ID_musician;
    int ID_winner;
    Date creat_date;
    Date fin_date;
    String descr;

    public Challenge(int ID, String name, int ID_song, int ID_musician, Date creat_date, Date fin_date, String descr) {
        this.ID = ID;
        this.name = name;
        this.ID_song = ID_song;
        this.ID_musician = ID_musician;
        this.creat_date = creat_date;
        this.fin_date = fin_date;
        this.descr = descr;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID_song() {
        return ID_song;
    }

    public void setID_song(int ID_song) {
        this.ID_song = ID_song;
    }

    public int getID_musician() {
        return ID_musician;
    }

    public void setID_musician(int ID_musician) {
        this.ID_musician = ID_musician;
    }

    public int getID_winner() {
        return ID_winner;
    }

    public void setID_winner(int ID_winner) {
        this.ID_winner = ID_winner;
    }

    public Date getCreat_date() {
        return creat_date;
    }

    public void setCreat_date(Date creat_date) {
        this.creat_date = creat_date;
    }

    public Date getFin_date() {
        return fin_date;
    }

    public void setFin_date(Date fin_date) {
        this.fin_date = fin_date;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
