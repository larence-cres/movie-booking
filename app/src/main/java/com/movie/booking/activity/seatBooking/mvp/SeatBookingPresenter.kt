package com.movie.booking.activity.seatBooking.mvp

import android.annotation.SuppressLint
import com.movie.booking.model.Movie
import com.movie.booking.model.Seat
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

/**
 * Created by Larence on 10/29/2020.
 */

@SuppressLint("CheckResult")
class SeatBookingPresenter(
    private val seatBookingView: SeatBookingView,
    private val seatBookingModel: SeatBookingModel,
) {

    // Initialize data
    private var mCompositeDisposable = CompositeDisposable()

    private var selectedId = ArrayList<Long>()
    private var movie = Movie()
    private var date = ""
    private var time = ""

    fun onCreate(movie: Movie, date: String, time: String) {
        seatBookingView.initData(movie, date, time)
        seatBookingView.initRecyclerView()
        this.movie = movie
        this.date = date
        this.time = time

        seatSelectionObservable()
        buyTicketObservable()
        backBtnObservable()
    }

    private fun seatSelectionObservable() {
        val seatObservable = seatObservable()
        seatBookingView.seatClickObservable().subscribe(seatObservable)
        mCompositeDisposable.add(seatObservable)
    }

    private fun seatObservable(): DisposableObserver<Seat> {
        return object : DisposableObserver<Seat>() {
            override fun onNext(seat: Seat) {
                val ids = seatBookingView.toggleDateSelection(seat)
                selectedId = ids
                seatBookingView.enableBuyTicketBtn(ids.size != 0)
            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }
        }
    }

    private fun buyTicketObservable() {
        seatBookingView.buyTicketObservable().subscribe {
            seatBookingModel.buyTicket(movie, date, time, selectedId)
        }
    }

    private fun backBtnObservable() {
        seatBookingView.backBtnObservable().subscribe {
            seatBookingModel.finishActivity()
        }
    }

    fun onDestroy() {
        mCompositeDisposable.clear()
    }

    fun finishActivityWithResult() {
        seatBookingModel.finishActivityWithResult()
    }

}