package com.movie.booking.activity.splashScreen.di

import androidx.appcompat.app.AppCompatActivity
import com.movie.booking.activity.splashScreen.mvp.SplashScreenModel
import com.movie.booking.activity.splashScreen.mvp.SplashScreenPresenter
import com.movie.booking.activity.splashScreen.mvp.SplashScreenView
import com.movie.booking.api.WebService
import com.movie.booking.app.dagger.AppActivity
import com.movie.booking.appUtils.SharedPreferenceUtils
import dagger.Module
import dagger.Provides

/**
 * Created by Larence on 10/29/2020.
 */

@Module
class SplashScreenModule(private val appCompatActivity: AppCompatActivity) {

    @AppActivity
    @Provides
    fun splashScreenView(): SplashScreenView {
        return SplashScreenView(appCompatActivity)
    }

    @AppActivity
    @Provides
    fun splashScreenPresenter(
        splashScreenView: SplashScreenView,
        splashScreenModel: SplashScreenModel
    ): SplashScreenPresenter {
        return SplashScreenPresenter(splashScreenView, splashScreenModel)
    }

    @AppActivity
    @Provides
    fun splashScreenModel(webService: WebService, sharedPreferenceUtils: SharedPreferenceUtils): SplashScreenModel {
        return SplashScreenModel(appCompatActivity, webService, sharedPreferenceUtils)
    }

}