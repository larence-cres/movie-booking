package com.movie.booking.activity.home.di

import com.movie.booking.activity.home.HomeActivity
import com.movie.booking.app.dagger.AppActivity
import com.movie.booking.app.dagger.AppComponent
import dagger.Component

/**
 * Created by Larence on 10/29/2020.
 */

@AppActivity
@Component(modules = [(HomeModule::class)], dependencies = [(AppComponent::class)])
interface HomeComponent {
    fun inject(homeActivity: HomeActivity)
}