package com.movie.booking.activity.buyTicket.mvp

import android.annotation.SuppressLint
import com.esewa.android.sdk.payment.ESewaConfiguration
import com.movie.booking.model.Movie
import io.reactivex.disposables.CompositeDisposable
import java.util.*

/**
 * Created by Larence on 10/29/2020.
 */

@SuppressLint("CheckResult")
class BuyTicketPresenter(
    private val buyTicketView: BuyTicketView,
    private val buyTicketModel: BuyTicketModel,
) {

    private var mCompositeDisposable = CompositeDisposable()
    private var eSewaConfiguration: ESewaConfiguration? = null

    fun onCreate(
        movie: Movie,
        date: String,
        time: String,
        selectedId: ArrayList<Long>,
    ) {
        eSewaConfiguration = buyTicketView.initESewaConfig()
        buyTicketView.initData(movie, date, time, selectedId)
//        validatePayBtn()
        buyTicketView.paymentMethod()
        payBtnObservable()
        onBackPressed()
    }

    private fun validatePayBtn() {
//        buyTicketView.enablePayBtn(buyTicketView.paymentMethod() != 0)
    }

    private fun payBtnObservable() {
        buyTicketView.payObservable().subscribe {
            when {
                buyTicketView.isESewa() ->
                    buyTicketModel.makePayment(
                        eSewaConfiguration!!,
                        buyTicketView.getBookedMovie()
                    )
                else -> storeData()
            }
        }
    }

    fun storeData() {
        buyTicketModel.saveTicketData(buyTicketView.getBookedMovie())
        buyTicketModel.finishActivityWithResult()
    }

    private fun onBackPressed() {
        buyTicketView.getBackObservable().subscribe { buyTicketModel.finishActivity() }
    }

    fun onDestroy() {
        mCompositeDisposable.clear()
    }
}