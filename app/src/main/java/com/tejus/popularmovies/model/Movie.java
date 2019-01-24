package com.tejus.popularmovies.model;

public class Movie {

    //Member variables
    private int id;
    private String title;
    private String posterPath;
    private String overview;
    private double popularity;
    private double rating;
    private int[] genreId;
    private boolean adult;
    private String releaseDate;

    //Constructor
    public Movie (int id, String posterPath, boolean adult) {
        this.id = id;
        this.posterPath = posterPath;
        this.adult = adult;
    }

    public Movie (Movie movie, String title, String overview, double popularity,
                  double rating, int[] genreId, String releaseDate) {
        this.id = movie.id;
        this.title = title;
        this.posterPath = movie.posterPath;
        this.overview = overview;
        this.popularity = popularity;
        this.rating = rating;
        this.genreId = genreId;
        this.adult = movie.adult;
        this.releaseDate = releaseDate;
    }

    public Movie (int id, String title, String posterPath, String overview,
                 double popularity, double rating, boolean adult, int[] genreId, String releaseDate) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.popularity = popularity;
        this.rating = rating;
        this.adult = adult;
        this.genreId = genreId;
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

    public int[] getGenreId() {
        return genreId;
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

    public void setGenreId(int[] genreId) {
        this.genreId = genreId;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
