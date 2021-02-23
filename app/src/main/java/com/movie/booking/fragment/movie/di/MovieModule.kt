package com.movie.booking.fragment.movie.di

import androidx.appcompat.app.AppCompatActivity
import com.movie.booking.app.dagger.AppActivity
import com.movie.booking.fragment.movie.mvp.MovieModel
import com.movie.booking.fragment.movie.mvp.MoviePresenter
import com.movie.booking.fragment.movie.mvp.MovieView
import com.movie.booking.api.WebService
import com.movie.booking.appUtils.SharedPreferenceUtils
import dagger.Module
import dagger.Provides

/**
 * Created by Larence on 10/29/2020.
 */

@Module
class MovieModule(private val appCompatActivity: AppCompatActivity) {

    @AppActivity
    @Provides
    fun movieView(): MovieView {
        return MovieView(appCompatActivity)
    }

    @AppActivity
    @Provides
    fun moviePresenter(movieView: MovieView, movieModel: MovieModel): MoviePresenter {
        return MoviePresenter(movieView, movieModel)
    }

    @AppActivity
    @Provides
    fun movieModel(
        webService: WebService,
        sharedPreferenceUtils: SharedPreferenceUtils,
    ): MovieModel {
        return MovieModel(appCompatActivity, sharedPreferenceUtils)
    }

}