package com.movie.booking.activity.home.mvp

import android.content.BroadcastReceiver
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.movie.booking.activity.seatBooking.SeatBookingActivity
import com.movie.booking.appUtils.AppConstants
import com.movie.booking.appUtils.SharedPreferenceUtils
import com.movie.booking.appUtils.Utils
import com.movie.booking.model.*
import com.movie.booking.model.Date
import org.json.JSONArray
import timber.log.Timber
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Larence on 10/29/2020.
 */

class HomeModel(
    private val appCompatActivity: AppCompatActivity,
    private val sharedPreferenceUtils: SharedPreferenceUtils,
) {

    fun registerReceiver(intent: BroadcastReceiver) {
        appCompatActivity.registerReceiver(intent, IntentFilter("bottomSheetIntent"))
    }

    fun unregisterReceiver(intent: BroadcastReceiver) {
        appCompatActivity.unregisterReceiver(intent)
    }

    fun startBookingActivity(selectedMovie: Movie, selectedDate: String, selectedTime: String) {
        SeatBookingActivity.start(appCompatActivity, selectedMovie, selectedDate, selectedTime)
    }

    fun getMovies(): ArrayList<Movie> {
        return Utils.getMovies(sharedPreferenceUtils[AppConstants.MOVIES])
    }

    fun getGenres(): ArrayList<Genre> {
        return Utils.getGenres(sharedPreferenceUtils[AppConstants.GENRES])
    }

    fun getTicketData(movieId: Long): BookedMovie {
        var ticketData = BookedMovie()
        val ticketList = Utils.getTickets(sharedPreferenceUtils[AppConstants.TICKET_DATA])
        for (ticket in ticketList) {
            if (ticket.movieId == movieId) {
                ticketData = ticket
            }
        }
        return ticketData
    }

    fun getBookedMovies(): ArrayList<Long> {
        val bookedMovies = ArrayList<Long>()
        val movieIds = sharedPreferenceUtils[AppConstants.BOOKED_MOVIE]
            .replace("[", "")
            .replace("]", "")
        if (movieIds.isNotEmpty()) {
            val idsList = movieIds.split(",").toTypedArray()
            for (ids in idsList) {
                bookedMovies.add(ids.trim().toLong())
            }
        }
        return bookedMovies
    }

    fun getDateForNext10Days(): ArrayList<Date> {
        val dates = ArrayList<Date>(10)
        val c = Calendar.getInstance()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dayOfWeekFormat = SimpleDateFormat("E", Locale.getDefault())
        val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())

        0.rangeTo(9).forEach { i ->
            c.add(Calendar.DATE, 1)
            val date = dateFormat.format(c.time)
            val dayOfWeek = dayOfWeekFormat.format(c.time)
            val month = monthFormat.format(c.time)
            val dateObj = Date()
            dateObj.date = date
            dateObj.dayOfWeek = dayOfWeek
            dateObj.month = month
            dateObj.day = c[Calendar.DATE].toString()
            dates.add(dateObj)
        }

        return dates
    }

}