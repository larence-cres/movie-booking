package com.movie.booking.activity.seatBooking.di

import com.movie.booking.activity.seatBooking.SeatBookingActivity
import com.movie.booking.app.dagger.AppActivity
import com.movie.booking.app.dagger.AppComponent
import dagger.Component

/**
 * Created by Larence on 10/29/2020.
 */

@AppActivity
@Component(modules = [(SeatBookingModule::class)], dependencies = [(AppComponent::class)])
interface SeatBookingComponent {
    fun inject(seatBookingActivity: SeatBookingActivity)
}