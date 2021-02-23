package com.movie.booking.activity.seatBooking.mvp

import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding2.view.RxView
import com.movie.booking.R
import com.movie.booking.appUtils.Utils
import com.movie.booking.adapter.seatBooking.SeatBookingAdapter
import com.movie.booking.appUtils.Utils.readJSONFromAsset
import com.movie.booking.model.Movie
import com.movie.booking.model.Seat
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_seat_booking.view.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by Larence on 10/29/2020.
 */

class SeatBookingView(
    private val appCompatActivity: AppCompatActivity,
    private val seatBookingAdapter: SeatBookingAdapter,
) : FrameLayout(appCompatActivity) {

    /**
     * Instantiate the layout file
     */
    init {
        View.inflate(appCompatActivity, R.layout.activity_seat_booking, this)
        appCompatActivity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        appCompatActivity.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    fun initData(movie: Movie, date: String, time: String) {
        tvHeader.text = movie.title
        tvDescription.text = "$time ${Utils.simpleDate(date)}"
    }

    fun initRecyclerView() {
        val jsonResponse = JSONObject(readJSONFromAsset(appCompatActivity, "seats.json"))

        val seats = ArrayList<Seat>()
        val seatList: JSONArray = jsonResponse.getJSONArray("seats")
        for (i in 0 until seatList.length()) {
            val seatObj = seatList.getJSONObject(i)
            val seat = Seat()
            seat.id = seatObj.optLong("id")
            seat.occupied = seatObj.optBoolean("occupied", false)
            seat.front = seatObj.optBoolean("front", false)
            seats.add(seat)
        }

        val manager = GridLayoutManager(appCompatActivity, 13)
        manager.reverseLayout = true
        rvSeats.layoutManager = manager
        seatBookingAdapter.setItems(seats)
        rvSeats.adapter = seatBookingAdapter
    }

    fun seatClickObservable(): Observable<Seat> {
        return seatBookingAdapter.getClickDetailObservable()
    }

    fun toggleDateSelection(selectedSeat: Seat): ArrayList<Long> {
        return seatBookingAdapter.updateSelection(selectedSeat)
    }

    fun enableBuyTicketBtn(enable: Boolean) {
        btnBuyTicket.isEnabled = enable
    }

    fun buyTicketObservable(): Observable<Any> {
        return RxView.clicks(btnBuyTicket)
    }

    fun backBtnObservable(): Observable<Any> {
        return RxView.clicks(ibBack)
    }

}