package com.movie.booking.activity.seatBooking

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import com.movie.booking.activity.seatBooking.di.DaggerSeatBookingComponent
import com.movie.booking.activity.seatBooking.di.SeatBookingModule
import com.movie.booking.activity.seatBooking.mvp.SeatBookingPresenter
import com.movie.booking.activity.seatBooking.mvp.SeatBookingView
import com.movie.booking.app.MyApplication
import com.movie.booking.appUtils.AppConstants
import com.movie.booking.model.Movie
import javax.inject.Inject

/**
 * Created by Larence on 10/29/2020.
 */

class SeatBookingActivity : AppCompatActivity() {

    @Inject
    lateinit var seatBookingView: SeatBookingView

    @Inject
    lateinit var seatBookingPresenter: SeatBookingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myApplication = MyApplication()
        DaggerSeatBookingComponent.builder()
            .appComponent(myApplication.get(this).appComponent())
            .seatBookingModule(SeatBookingModule(this))
            .build()
            .inject(this)

        setContentView(seatBookingView)
        seatBookingPresenter.onCreate(movie, date, time)
    }

    companion object {
        var movie = Movie()
        var date = ""
        var time = ""
        fun start(appCompatActivity: AppCompatActivity, movie: Movie, date: String, time: String) {
            startActivityForResult(appCompatActivity,
                Intent(appCompatActivity, SeatBookingActivity::class.java),
                AppConstants.REQUEST_CODE,
                null)
            this.movie = movie
            this.date = date
            this.time = time
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED) {
            return
        }

//        if (resultCode == Activity.RESULT_OK) {
        if (requestCode == AppConstants.REQUEST_CODE) {
            seatBookingPresenter.finishActivityWithResult()
        }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        seatBookingPresenter.onDestroy()
    }
}