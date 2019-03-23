package com.tejus.popularmovies.utilities;

import com.tejus.popularmovies.model.Movie;
import com.tejus.popularmovies.model.MovieResult;
import com.tejus.popularmovies.model.Review;
import com.tejus.popularmovies.model.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitEndpoints {

    String API_PARAM = "api_key";
    String LANGUAGE_PARAM = "language";
    String PAGE_PARAM = "page";

    @GET("3/movie/{sort}")
    Call<MovieResult<Movie>> getMovies(@Path("sort") String sortMode,
                                       @Query(API_PARAM) String api,
                                       @Query(LANGUAGE_PARAM) String language,
                                       @Query(PAGE_PARAM) String page);

    @GET("3/movie/{id}/reviews")
    Call<MovieResult<Review>> getReviews(@Path("id") int id,
                                         @Query(LANGUAGE_PARAM) String language,
                                         @Query(API_PARAM) String api);

    @GET("3/movie/{id}/videos")
    Call<MovieResult<Video>> getVideos(@Path("id") int id,
                                       @Query(LANGUAGE_PARAM) String language,
                                       @Query(API_PARAM) String api);
}
