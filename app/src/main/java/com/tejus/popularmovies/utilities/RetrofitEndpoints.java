package com.tejus.popularmovies.utilities;

import com.tejus.popularmovies.model.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitEndpoints {

    static final String API_PARAM = "api_key";
    static final String LANGUAGE_PARAM = "language";
    static final String PAGE_PARAM = "page";

    @GET("3/movie/{sort}")
    Call<MovieResult> getMovies(@Path("sort") String sortMode,
                                @Query(API_PARAM) String api,
                                @Query(LANGUAGE_PARAM) String language,
                                @Query(PAGE_PARAM) String page);
}
