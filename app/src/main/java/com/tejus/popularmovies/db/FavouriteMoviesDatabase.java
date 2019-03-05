package com.tejus.popularmovies.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.tejus.popularmovies.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class FavouriteMoviesDatabase extends RoomDatabase {

    private static final String LOG_TAG = FavouriteMoviesDatabase.class.getSimpleName();

    private static final String DATABASE_NAME = "favourite_movies";
    private static FavouriteMoviesDatabase sInstance;

    public static FavouriteMoviesDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (FavouriteMoviesDatabase.class) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        FavouriteMoviesDatabase.class,
                        FavouriteMoviesDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Returning database instance");
        return sInstance;
    }

    public abstract FavouriteMoviesDao favouriteMoviesDao();
}
