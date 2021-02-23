package com.movie.booking.activity.splashScreen.mvp

import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.movie.booking.activity.home.HomeActivity
import com.movie.booking.api.ApiUrls
import com.movie.booking.api.WebService
import com.movie.booking.appUtils.AppConstants
import com.movie.booking.appUtils.SharedPreferenceUtils
import com.movie.booking.model.Genre
import com.movie.booking.model.GenreDAO
import com.movie.booking.model.Movie
import com.movie.booking.model.MovieDAO
import io.reactivex.Observable


/**
 * Created by Larence on 10/29/2020.
 */

class SplashScreenModel(
    private val appCompatActivity: AppCompatActivity,
    private val webService: WebService,
    private val sharedPreferenceUtils: SharedPreferenceUtils,
) {

    /**
     * Returns movies from the server
     */
    fun getMovies(): Observable<MovieDAO> {
        val url = ApiUrls.NOW_PLAYING
        return webService.getMovies(url)
    }

    /**
     * Returns genres from the server
     */
    fun getGenre(): Observable<GenreDAO> {
        val url = ApiUrls.GENRE
        return webService.getGenre(url)
    }

    /**
     * Stores the list of movies in the SharedPreferences
     * @param movies
     */
    fun saveMovies(movies: ArrayList<Movie>) {
        val moviesGson = Gson().toJson(movies)
        sharedPreferenceUtils.save(AppConstants.MOVIES, moviesGson)
    }

    /**
     * Stores the list of genres in the SharedPreferences
     * @param genres
     */
    fun saveGenres(genres: ArrayList<Genre>) {
        val genreGson = Gson().toJson(genres)
        sharedPreferenceUtils.save(AppConstants.GENRES, genreGson)
    }

    /**
     * Starts home activity
     */
    fun startHomeActivity() {
        HomeActivity.start(appCompatActivity)
        finishActivity()
    }

    /**
     * Finishes the activity
     */
    private fun finishActivity() {
        appCompatActivity.finish()
    }

}