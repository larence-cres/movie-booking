package com.movie.booking.activity.splashScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.movie.booking.activity.splashScreen.di.DaggerSplashScreenComponent
import com.movie.booking.activity.splashScreen.di.SplashScreenModule
import com.movie.booking.activity.splashScreen.mvp.SplashScreenPresenter
import com.movie.booking.activity.splashScreen.mvp.SplashScreenView
import com.movie.booking.app.MyApplication
import javax.inject.Inject

/**
 * Created by Larence on 10/29/2020.
 */

class SplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var splashScreenView: SplashScreenView

    @Inject
    lateinit var splashScreenPresenter: SplashScreenPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myApplication = MyApplication()
        DaggerSplashScreenComponent.builder()
            .appComponent(myApplication.get(this).appComponent())
            .splashScreenModule(SplashScreenModule(this))
            .build()
            .inject(this)
        setContentView(splashScreenView)
        splashScreenPresenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        splashScreenPresenter.onDestroy()
    }
}