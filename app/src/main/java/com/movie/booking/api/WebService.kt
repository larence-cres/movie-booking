package com.movie.booking.api

import com.movie.booking.model.GenreDAO
import com.movie.booking.model.MovieDAO
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by Larence on 10/29/2020.
 */

interface WebService {

    @GET
    fun getMovies(@Url url: String): Observable<MovieDAO>

    @GET
    fun getGenre(@Url url: String): Observable<GenreDAO>

}