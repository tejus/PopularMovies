package com.tejus.popularmovies.model;

import com.google.gson.annotations.SerializedName;

public class Movie {

    //Member variables
    private int id;
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    private String overview;
    private double popularity;
    @SerializedName("vote_average")
    private double rating;
    private int[] genreIds;
    private boolean adult;
    private String releaseDate;

    //Constructor
    public Movie(int id, String title, String posterPath, String overview,
                 double popularity, double rating, boolean adult, int[] genreIds, String releaseDate) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.popularity = popularity;
        this.rating = rating;
        this.adult = adult;
        this.genreIds = genreIds;
        this.releaseDate = releaseDate;
    }

    //Getter methods
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getRating() {
        return rating;
    }

    public boolean isAdult() {
        return adult;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    //Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setGenreIds(int[] genreIds) {
        this.genreIds = genreIds;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
