package com.movie.booking.activity.buyTicket.mvp

import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.esewa.android.sdk.payment.ESewaConfiguration
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.jakewharton.rxbinding2.view.RxView
import com.movie.booking.R
import com.movie.booking.api.ApiUrls
import com.movie.booking.appUtils.AppConstants
import com.movie.booking.appUtils.SharedPreferenceUtils
import com.movie.booking.appUtils.Utils
import com.movie.booking.dialog.ticketDialog.TicketDialog
import com.movie.booking.model.BookedMovie
import com.movie.booking.model.Movie
import com.movie.booking.widget.QRCodeHelper
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_buy_ticket.view.*
import kotlinx.android.synthetic.main.activity_buy_ticket.view.ivPoster
import kotlinx.android.synthetic.main.activity_buy_ticket.view.tvMovieName
import kotlinx.android.synthetic.main.detail_bottom_sheet.view.*
import org.json.JSONObject
import timber.log.Timber
import java.util.ArrayList

/**
 * Created by Larence on 10/29/2020.
 */

class BuyTicketView(
    private val appCompatActivity: AppCompatActivity,
) : FrameLayout(appCompatActivity) {

    private val bookedMovie = BookedMovie()
    private var eSewa = false
    private var totalPayable = 0.0

    init {
        View.inflate(appCompatActivity, R.layout.activity_buy_ticket, this)
        appCompatActivity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        appCompatActivity.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Utils.hideKeyboardOnStartUp(appCompatActivity)
    }

    fun initESewaConfig(): ESewaConfiguration {
        return ESewaConfiguration()
            .clientId(AppConstants.MERCHANT_ID)
            .secretKey(AppConstants.MERCHANT_SECRET_KEY)
            .environment(AppConstants.CONFIG_ENVIRONMENT)
    }

    fun initData(movie: Movie, date: String, time: String, selectedId: ArrayList<Long>) {
        Picasso.get()
            .load(ApiUrls.IMAGE_PATH + movie.posterPath)
            .transform(RoundedCornersTransformation(120, 0))
            .placeholder(R.drawable.img_gallery)
            .error(R.drawable.img_gallery)
            .into(ivPoster, object : Callback {
                override fun onSuccess() {}

                override fun onError(exception: Exception?) {
                    Timber.e("onError : ${exception!!.localizedMessage}")
                }
            })

        tvMovieName.text = movie.title
        tvDateTime.text = "$time ${Utils.simpleDate(date)}"
        tvTicket.text = "Tickets: ${selectedId.size}"

        val subTotal = AppConstants.PRICE * selectedId.size
        val totalCharge = AppConstants.CHARGE * selectedId.size
        totalPayable = subTotal + totalCharge

        tvPrice.text = "Rs. $subTotal"
        tvCharge.text = "Rs. $totalCharge"
        tvTotal.text = "Rs. $totalPayable"

        btnPay.text = "PAY Rs.${totalPayable}"

        bookedMovie.movieId = movie.id
        bookedMovie.movieName = movie.title!!
        bookedMovie.ticketId = Utils.getRandomString(16)
        bookedMovie.date = date
        bookedMovie.time = time
        bookedMovie.seat = selectedId.toString()
        bookedMovie.price = totalPayable
        bookedMovie.posterPath = movie.posterPath!!
    }

    fun paymentMethod() {
        rbESewa.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                btnPay.text = "PAY Rs.${totalPayable}"
                eSewa = true
                enablePayBtn(true)
            }
        }
        rbCash.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                btnPay.text = "Book"
                eSewa = false
                enablePayBtn(true)
            }
        }
    }

    fun isESewa(): Boolean {
        return eSewa
    }

    fun getBookedMovie(): BookedMovie {
        return bookedMovie
    }

    fun enablePayBtn(enable: Boolean) {
        btnPay.isEnabled = enable
    }

    fun payObservable(): Observable<Any> {
        return RxView.clicks(btnPay)
    }

    fun getBackObservable(): Observable<Any> {
        return RxView.clicks(ibBack)
    }
}