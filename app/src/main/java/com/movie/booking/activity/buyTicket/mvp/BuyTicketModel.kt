package com.movie.booking.activity.buyTicket.mvp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import com.esewa.android.sdk.payment.ESewaConfiguration
import com.esewa.android.sdk.payment.ESewaPayment
import com.esewa.android.sdk.payment.ESewaPaymentActivity
import com.google.gson.Gson
import com.movie.booking.api.ApiConstants
import com.movie.booking.api.ApiUrls
import com.movie.booking.api.WebService
import com.movie.booking.appUtils.AppConstants
import com.movie.booking.appUtils.AppConstants.REQUEST_CODE_PAYMENT
import com.movie.booking.appUtils.SharedPreferenceUtils
import com.movie.booking.appUtils.Utils
import com.movie.booking.model.BookedMovie


/**
 * Created by Larence on 10/29/2020.
 */

class BuyTicketModel(
    private val appCompatActivity: AppCompatActivity,
    private val sharedPreferenceUtils: SharedPreferenceUtils,
) {

    fun finishActivityWithResult() {
        val intent = Intent()
        appCompatActivity.setResult(AppConstants.REQUEST_CODE, intent)
        appCompatActivity.finish()
    }

    fun finishActivity() {
        appCompatActivity.finish()
    }

    fun makePayment(eSewaConfiguration: ESewaConfiguration, bookedMovie: BookedMovie) {
        val eSewaPayment = ESewaPayment(bookedMovie.price.toString(),
            bookedMovie.movieName,
            bookedMovie.ticketId,
            ApiUrls.CALLBACK_URL)
        val intent = Intent(appCompatActivity, ESewaPaymentActivity::class.java)
        intent.putExtra(ESewaConfiguration.ESEWA_CONFIGURATION, eSewaConfiguration)
        intent.putExtra(ESewaPayment.ESEWA_PAYMENT, eSewaPayment)
        appCompatActivity.startActivityForResult(intent, REQUEST_CODE_PAYMENT)
    }


    fun saveTicketData(ticketData: BookedMovie) {
        val bookedMovieIds = ArrayList<Long>()
        val bookedMovies = ArrayList<BookedMovie>()
        val movieIds = sharedPreferenceUtils[AppConstants.BOOKED_MOVIE]
            .replace("[", "")
            .replace("]", "")
        when {
            movieIds.isNotEmpty() -> {
                val bookedList = movieIds.split(",").toTypedArray()
                bookedList.mapTo(bookedMovieIds) { it.trim().toLong() }
            }
        }
        when {
            sharedPreferenceUtils[AppConstants.TICKET_DATA].isNotEmpty() -> {
                Utils.getTickets(sharedPreferenceUtils[AppConstants.TICKET_DATA])
                    .forEach { bookedMovie ->
                        bookedMovies.add(bookedMovie)
                    }
            }
        }
        bookedMovieIds.add(ticketData.movieId)
        sharedPreferenceUtils.save(AppConstants.BOOKED_MOVIE, bookedMovieIds.toString())

        bookedMovies.add(ticketData)
        val ticketsGson = Gson().toJson(bookedMovies)
        sharedPreferenceUtils.save(AppConstants.TICKET_DATA, ticketsGson)
    }

}