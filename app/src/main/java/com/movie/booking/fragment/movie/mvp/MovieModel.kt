package com.movie.booking.fragment.movie.mvp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.movie.booking.api.ApiUrls
import com.movie.booking.api.WebService
import com.movie.booking.appUtils.AppConstants
import com.movie.booking.appUtils.SharedPreferenceUtils
import com.movie.booking.model.Genre
import io.reactivex.Observable
import java.lang.reflect.Type

/**
 * Created by Larence on 10/29/2020.
 */

class MovieModel(
    private val appCompatActivity: AppCompatActivity,
    private val sharedPreferenceUtils: SharedPreferenceUtils
) {


    fun setBottomSheetIntent() {
        val intent = Intent()
        intent.action = "bottomSheetIntent"
        appCompatActivity.sendBroadcast(intent)
    }

    fun getGenres(): ArrayList<Genre> {
        val gson = Gson()
        val json: String = sharedPreferenceUtils[AppConstants.GENRES]
        val type: Type = object : TypeToken<ArrayList<Genre>>() {}.type
        return gson.fromJson(json, type)
    }

}