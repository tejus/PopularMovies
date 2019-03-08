package com.tejus.popularmovies.utilities;

import com.tejus.popularmovies.model.Movie;
import com.tejus.popularmovies.model.MovieResult;
import com.tejus.popularmovies.model.Reviews;
import com.tejus.popularmovies.model.Videos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitEndpoints {

    static final String API_PARAM = "api_key";
    static final String LANGUAGE_PARAM = "language";
    static final String PAGE_PARAM = "page";

    @GET("3/movie/{sort}")
    Call<MovieResult<Movie>> getMovies(@Path("sort") String sortMode,
                                       @Query(API_PARAM) String api,
                                       @Query(LANGUAGE_PARAM) String language,
                                       @Query(PAGE_PARAM) String page);

    @GET("3/movie/{id}/reviews")
    Call<MovieResult<Reviews>> getReviews(@Path("id") int id,
                                          @Query(LANGUAGE_PARAM) String language,
                                          @Query(API_PARAM) String api);

    @GET("3/movie/{id}/videos")
    Call<MovieResult<Videos>> getVideos(@Path("id") int id,
                                        @Query(LANGUAGE_PARAM) String language,
                                        @Query(API_PARAM) String api);
}
