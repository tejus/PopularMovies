package com.tejus.popularmovies.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.tejus.popularmovies.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class FavouriteMoviesDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "favourite_movies";
    private static FavouriteMoviesDatabase sInstance;

    public static FavouriteMoviesDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (FavouriteMoviesDatabase.class) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        FavouriteMoviesDatabase.class,
                        FavouriteMoviesDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract FavouriteMoviesDao favouriteMoviesDao();
}
