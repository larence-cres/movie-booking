package com.movie.booking.appUtils

import com.esewa.android.sdk.payment.ESewaConfiguration




/**
 * Created by Larence on 10/29/2020.
 */

object AppConstants {

    const val SHARED_PREFERENCE = "SHARED_PREFERENCE"
    const val LOG_TAG = "DEBUG"
    const val TIME_OUT = "Time Out"
    const val NO_NETWORK = "No Network Connection"
    const val SUCCESS = "Success"
    const val ERROR = "Error"

    const val CONFIG_ENVIRONMENT = ESewaConfiguration.ENVIRONMENT_TEST
    const val MERCHANT_ID = "<your-esewa-merchant-id>"
    const val MERCHANT_SECRET_KEY = "<your-esewa-merchant-secret-key>"
    const val REQUEST_CODE_PAYMENT = 1

    const val MOVIES = "MOVIE"
    const val GENRES = "GENRE"
    const val ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm-"
    const val REQUEST_CODE = 101
    const val TICKET_DATA = "TICKET_DATA"
    const val BOOKED_MOVIE = "BOOKED_MOVIE"

    const val PRICE = 100.00
    const val CHARGE = 10.00

}