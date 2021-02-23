package com.movie.booking.activity.home.mvp

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import com.movie.booking.model.Date
import com.movie.booking.model.Genre
import com.movie.booking.model.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Larence on 10/29/2020.
 */

@SuppressLint("CheckResult")
class HomePresenter(
    private val homeView: HomeView,
    private val homeModel: HomeModel,
) : DialogInterface.OnDismissListener {

    private var mCompositeDisposable = CompositeDisposable()

    private val intent = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            homeView.setToBroadcast(true)
            homeView.expandBottomSheet()
        }
    }

    fun onCreate() {
        homeView.setData(getMovies(), getGenres(), getBookedMovies())
        homeView.initViewPager()
        homeView.dialogDismissListener(this)
        homeModel.registerReceiver(intent)
        homeView.bottomSheetStateChange()
        homeView.calendarSetup(homeModel.getDateForNext10Days())
        homeView.updateRadioGroup()
        dateSelectionObservable()
        bookSeatObservable()
    }

    fun getMovies(): ArrayList<Movie> {
        return homeModel.getMovies()
    }

    fun getGenres(): ArrayList<Genre> {
        return homeModel.getGenres()
    }

    fun getBookedMovies(): ArrayList<Long> {
        return homeModel.getBookedMovies()
    }

    private fun dateSelectionObservable() {
        val dateObservable = dateObservable()
        homeView.dateClickObservable().subscribe(dateObservable)
        mCompositeDisposable.add(dateObservable)
    }

    private fun dateObservable(): DisposableObserver<Date> {
        return object : DisposableObserver<Date>() {
            override fun onNext(date: Date) {
                homeView.setSelectedDate(date.date!!)
                homeView.toggleDateSelection(date)
            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }
        }
    }

    private fun bookSeatObservable() {
        homeView.bookSeatObservable().subscribe {
            if (homeView.isBookedMovie()) {
                showTicket()
            } else {
                homeModel.startBookingActivity(homeView.getSelectedMovie(),
                    homeView.getSelectedDate(),
                    homeView.getSelectedTime())
            }
        }
    }

    /**
     * Shows the ticket dialog
     */
    fun showTicket() {
        homeView.setToBroadcast(false)
        homeView.collapseBottomSheet()

        val ticketDialogViewObservable = ticketDialogObservable()
        homeView.showTicket().subscribe(ticketDialogViewObservable)
        mCompositeDisposable.add(ticketDialogViewObservable)

        homeView.setTicketData(homeModel.getTicketData(homeView.getSelectedMovie().id))

        Timer("SubscribeIntent", false).schedule(object : TimerTask() {
            override fun run() {
                homeView.setViewIntent(0F)
            }
        }, 50)

    }

    private fun ticketDialogObservable(): DisposableObserver<Boolean> {
        return object : DisposableObserver<Boolean>() {
            override fun onNext(boolean: Boolean) {}
            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        }
    }

    fun onDestroy() {
        mCompositeDisposable.clear()
        homeModel.unregisterReceiver(intent)
    }

    override fun onDismiss(dialog: DialogInterface?) {
        homeView.setViewIntent(1F)
    }

}