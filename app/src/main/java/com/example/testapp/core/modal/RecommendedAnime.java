package com.example.testapp.core.modal;

public class RecommendedAnime {

    public String title;
    public String rating;
    public String episodes;
    public int imageResId;

    public RecommendedAnime(String title, String rating, String episodes, int imageResId) {
        this.title = title;
        this.rating = rating;
        this.episodes = episodes;
        this.imageResId = imageResId;
    }
}
