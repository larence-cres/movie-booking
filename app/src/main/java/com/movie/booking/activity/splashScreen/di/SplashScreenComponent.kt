package com.movie.booking.activity.splashScreen.di

import com.movie.booking.activity.splashScreen.SplashScreenActivity
import com.movie.booking.app.dagger.AppActivity
import com.movie.booking.app.dagger.AppComponent
import dagger.Component

/**
 * Created by Larence on 10/29/2020.
 */

@AppActivity
@Component(modules = [(SplashScreenModule::class)], dependencies = [(AppComponent::class)])
interface SplashScreenComponent {
    fun inject(splashScreenActivity: SplashScreenActivity)
}