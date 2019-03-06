package com.tejus.popularmovies.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.tejus.popularmovies.model.Movie;

import java.util.List;

@Dao
public interface FavouriteMoviesDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> loadAllMovies();

    @Query("SELECT COUNT(id) FROM movies WHERE id = :id")
    int searchMovieById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Query("DELETE FROM movies WHERE id = :id")
    void deleteMovieById(int id);

    @Query("DELETE FROM movies")
    void deleteAllMovies();
}
