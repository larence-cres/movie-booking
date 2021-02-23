package com.movie.booking.activity.buyTicket

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import com.esewa.android.sdk.payment.ESewaPayment
import com.movie.booking.activity.buyTicket.di.DaggerBuyTicketComponent
import com.movie.booking.activity.buyTicket.di.BuyTicketModule
import com.movie.booking.activity.buyTicket.mvp.BuyTicketPresenter
import com.movie.booking.activity.buyTicket.mvp.BuyTicketView
import com.movie.booking.app.MyApplication
import com.movie.booking.appUtils.AppConstants
import com.movie.booking.appUtils.AppConstants.REQUEST_CODE_PAYMENT
import com.movie.booking.model.Movie
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject

/**
 * Created by Larence on 10/29/2020.
 */

class BuyTicketActivity : AppCompatActivity() {

    @Inject
    lateinit var buyTicketView: BuyTicketView

    @Inject
    lateinit var buyTicketPresenter: BuyTicketPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myApplication = MyApplication()
        DaggerBuyTicketComponent.builder()
            .appComponent(myApplication.get(this).appComponent())
            .buyTicketModule(BuyTicketModule(this))
            .build()
            .inject(this)

        setContentView(buyTicketView)
        buyTicketPresenter.onCreate(movie, date, time, selectedId)
    }

    companion object {

        var movie = Movie()
        var date = ""
        var time = ""
        var selectedId = ArrayList<Long>()

        fun start(
            appCompatActivity: AppCompatActivity,
            movie: Movie,
            date: String,
            time: String,
            selectedId: ArrayList<Long>,
        ) {
            startActivityForResult(appCompatActivity,
                Intent(appCompatActivity, BuyTicketActivity::class.java),
                AppConstants.REQUEST_CODE,
                null)
            this.movie = movie
            this.date = date
            this.time = time
            this.selectedId = selectedId
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PAYMENT) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val s = data!!.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE)
                    Timber.e("Proof of Payment RESULT_OK : $s")
                    Toast.makeText(this, "SUCCESSFUL PAYMENT", Toast.LENGTH_SHORT).show()
                    buyTicketPresenter.storeData()
                }
                Activity.RESULT_CANCELED -> {
                    Toast.makeText(this, "Payment canceled", Toast.LENGTH_SHORT).show()
                }
                ESewaPayment.RESULT_EXTRAS_INVALID -> {
                    val s = data!!.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE)
                    Timber.e("Proof of Payment : $s")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        buyTicketPresenter.onDestroy()
    }
}