package com.tejus.popularmovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.tejus.popularmovies.utilities.NetworkUtils;

@Entity(tableName = "movies")
public class Movie implements Parcelable {

    //Member variables
    @PrimaryKey
    private int id;
    private String title;

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    private String posterPath;
    private String overview;
    private double popularity;
    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    private double rating;
    /*    @SerializedName("genre_ids")
        @ColumnInfo(name = "genre_ids")
        private int[] genreIds;*/
    private boolean adult;
    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    private String releaseDate;

    //Constructor
    public Movie(int id, String title, String posterPath, String overview,
                 double popularity, double rating, boolean adult, String releaseDate) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.popularity = popularity;
        this.rating = rating;
//        this.genreIds = genreIds;
        this.adult = adult;
        this.releaseDate = releaseDate;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        popularity = in.readDouble();
        rating = in.readDouble();
        adult = in.readByte() != 0;
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    public Uri getPosterPathUri() {
        return NetworkUtils.fetchPosterPath(posterPath);
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

    /*public int[] getGenreIds() {
        return genreIds;
    }*/

    public boolean isAdult() {
        return adult;
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

    /*public void setGenreIds(int[] genreIds) {
        this.genreIds = genreIds;
    }*/

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeDouble(popularity);
        dest.writeDouble(rating);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(releaseDate);
    }
}
