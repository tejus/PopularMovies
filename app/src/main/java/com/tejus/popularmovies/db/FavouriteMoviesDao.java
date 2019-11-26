package com.tejus.popularmovies.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tejus.popularmovies.model.Movie;

import java.util.List;

@Dao
public interface FavouriteMoviesDao {

    //Fetch the earliest stored movie first (FIFO)
    @Query("SELECT * FROM movies ORDER BY dbId ASC")
    LiveData<List<Movie>> loadAllMovies();

    @Query("SELECT COUNT(id) FROM movies WHERE id = :id")
    int searchMovieById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Query("DELETE FROM movies WHERE id = :id")
    void deleteMovieById(int id);
}
