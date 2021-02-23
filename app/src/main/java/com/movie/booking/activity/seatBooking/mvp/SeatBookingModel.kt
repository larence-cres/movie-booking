package com.movie.booking.activity.seatBooking.mvp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.movie.booking.api.WebService
import com.movie.booking.activity.buyTicket.BuyTicketActivity
import com.movie.booking.appUtils.AppConstants
import com.movie.booking.model.Movie
import java.util.ArrayList


/**
 * Created by Larence on 10/29/2020.
 */

class SeatBookingModel(
    private val appCompatActivity: AppCompatActivity,
    private val webService: WebService
) {

    fun buyTicket(movie: Movie, date: String, time: String, selectedId: ArrayList<Long>) {
        BuyTicketActivity.start(appCompatActivity, movie, date, time, selectedId)
    }

    fun finishActivityWithResult(){
        val intent = Intent()
        appCompatActivity.setResult(AppConstants.REQUEST_CODE, intent)
        appCompatActivity.finish()
    }

    fun finishActivity(){
        appCompatActivity.finish()
    }

}