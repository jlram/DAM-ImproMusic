package com.example.josluis.impromusic.Tablas;

import java.util.Date;

public class Participation {

    private int ID_musician;
    private int ID_chall;
    private Date part_date;
    private int votes;
    private String youtube;

    public Participation(int ID_musician, int ID_chall, Date part_date, int votes, String youtube) {
        this.ID_musician = ID_musician;
        this.ID_chall = ID_chall;
        this.part_date = part_date;
        this.votes = votes;
        this.youtube = youtube;
    }

    public int getID_musician() {
        return ID_musician;
    }

    public void setID_musician(int ID_musician) {
        this.ID_musician = ID_musician;
    }

    public int getID_chall() {
        return ID_chall;
    }

    public void setID_chall(int ID_chall) {
        this.ID_chall = ID_chall;
    }

    public Date getPart_date() {
        return part_date;
    }

    public void setPart_date(Date part_date) {
        this.part_date = part_date;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }
}
