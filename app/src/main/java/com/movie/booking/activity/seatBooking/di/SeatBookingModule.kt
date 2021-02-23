package com.movie.booking.activity.seatBooking.di

import androidx.appcompat.app.AppCompatActivity
import com.movie.booking.activity.seatBooking.mvp.SeatBookingModel
import com.movie.booking.activity.seatBooking.mvp.SeatBookingPresenter
import com.movie.booking.activity.seatBooking.mvp.SeatBookingView
import com.movie.booking.adapter.seatBooking.SeatBookingAdapter
import com.movie.booking.api.WebService
import com.movie.booking.app.dagger.AppActivity
import dagger.Module
import dagger.Provides

/**
 * Created by Larence on 10/29/2020.
 */

@Module
class SeatBookingModule(private val appCompatActivity: AppCompatActivity) {

    @AppActivity
    @Provides
    fun seatBookingView(seatBookingAdapter: SeatBookingAdapter): SeatBookingView {
        return SeatBookingView(appCompatActivity, seatBookingAdapter)
    }

    @AppActivity
    @Provides
    fun seatBookingPresenter(
        seatBookingView: SeatBookingView,
        seatBookingModel: SeatBookingModel,
    ): SeatBookingPresenter {
        return SeatBookingPresenter(seatBookingView, seatBookingModel)
    }

    @AppActivity
    @Provides
    fun seatBookingModel(webService: WebService): SeatBookingModel {
        return SeatBookingModel(appCompatActivity, webService)
    }

    @AppActivity
    @Provides
    fun seatBookingAdapter(): SeatBookingAdapter {
        return SeatBookingAdapter(appCompatActivity)
    }

}