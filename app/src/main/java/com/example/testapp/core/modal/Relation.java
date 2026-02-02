package com.example.testapp.core.modal;

public class Relation {

    public String title;
    public String type;

    public String rating;
    public String episodes;

    public int imageResId;

    public Relation(String title, String type, String rating, String episodes, int imageResId) {
        this.title = title;
        this.type = type;
        this.rating = rating;
        this.episodes = episodes;
        this.imageResId = imageResId;
    }
}
