package com.movie.booking.fragment.movie.di

import com.movie.booking.app.dagger.AppActivity
import com.movie.booking.app.dagger.AppComponent
import com.movie.booking.fragment.movie.MovieFragment
import dagger.Component

/**
 * Created by Larence on 10/29/2020.
 */

@AppActivity
@Component(modules = [(MovieModule::class)], dependencies = [(AppComponent::class)])
interface MovieComponent {
    fun inject(movieFragment: MovieFragment)
}