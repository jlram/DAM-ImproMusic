package com.example.josluis.impromusic.Tablas;

/**
 * Created by jlram on 21/04/2018.
 */

public class Song {

    private int ID;
    private String name;
    private String author;
    private String album;
    private String genre;
    private String cover;
    private String link;

    public Song(int ID, String name, String author, String album, String genre, String cover, String link) {
        this.ID = ID;
        this.name = name;
        this.author = author;
        this.album = album;
        this.genre = genre;
        this.cover = cover;
        this.link = link;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
