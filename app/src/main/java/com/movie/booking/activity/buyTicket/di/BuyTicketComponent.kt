package com.movie.booking.activity.buyTicket.di

import com.movie.booking.activity.buyTicket.BuyTicketActivity
import com.movie.booking.app.dagger.AppActivity
import com.movie.booking.app.dagger.AppComponent
import dagger.Component

/**
 * Created by Larence on 10/29/2020.
 */

@AppActivity
@Component(modules = [(BuyTicketModule::class)], dependencies = [(AppComponent::class)])
interface BuyTicketComponent {
    fun inject(buyTicketActivity: BuyTicketActivity)
}