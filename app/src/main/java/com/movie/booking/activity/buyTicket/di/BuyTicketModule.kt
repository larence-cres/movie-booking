package com.movie.booking.activity.buyTicket.di

import androidx.appcompat.app.AppCompatActivity
import com.movie.booking.activity.buyTicket.mvp.*
import com.movie.booking.api.WebService
import com.movie.booking.app.dagger.AppActivity
import com.movie.booking.appUtils.SharedPreferenceUtils
import dagger.Module
import dagger.Provides

/**
 * Created by Larence on 10/29/2020.
 */

@Module
class BuyTicketModule(private val appCompatActivity: AppCompatActivity) {

    @AppActivity
    @Provides
    fun buyTicketView(): BuyTicketView {
        return BuyTicketView(
            appCompatActivity
        )
    }

    @AppActivity
    @Provides
    fun buyTicketPresenter(
        buyTicketView: BuyTicketView,
        buyTicketModel: BuyTicketModel,
    ): BuyTicketPresenter {
        return BuyTicketPresenter(buyTicketView, buyTicketModel)
    }

    @AppActivity
    @Provides
    fun buyTicketModel(
        sharedPreferenceUtils: SharedPreferenceUtils,
    ): BuyTicketModel {
        return BuyTicketModel(appCompatActivity, sharedPreferenceUtils)
    }

}