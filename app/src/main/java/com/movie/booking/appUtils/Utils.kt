package com.movie.booking.appUtils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Parcelable
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.movie.booking.appUtils.AppConstants.ALLOWED_CHARACTERS
import com.movie.booking.model.BookedMovie
import com.movie.booking.model.Genre
import com.movie.booking.model.Movie
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Larence on 10/29/2020.
 */

object Utils {

    fun hideKeyboard(appCompatActivity: AppCompatActivity) {
        val view = appCompatActivity.currentFocus
        val imm =
            appCompatActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideKeyboardOnStartUp(appCompatActivity: AppCompatActivity) {
        appCompatActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun readJSONFromAsset(appCompatActivity: AppCompatActivity, fileName: String): String? {
        var json: String? = null
        try {
            val inputStream: InputStream = appCompatActivity.assets.open(fileName)
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun stringToJSON(jsonData: String): JSONObject {
        return JSONObject(jsonData)
    }

    fun simpleDate(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        return outputFormat.format(inputFormat.parse(date))
    }

    fun getRandomString(sizeOfRandomString: Int): String {
        val random = Random()
        val sb = StringBuilder(sizeOfRandomString)
        for (i in 0 until sizeOfRandomString)
            sb.append(ALLOWED_CHARACTERS[random.nextInt(ALLOWED_CHARACTERS.length)])
        return sb.toString()
    }

    fun getMovies(data: String): ArrayList<Movie> {
        val gson = Gson()
        val json: String = data
        val type: Type = object : TypeToken<ArrayList<Movie>>() {}.type
        return gson.fromJson(json, type)
    }

    fun getGenres(data: String): ArrayList<Genre> {
        val gson = Gson()
        val json: String = data
        val type: Type = object : TypeToken<ArrayList<Genre>>() {}.type
        return gson.fromJson(json, type)
    }

    fun getTickets(data: String): ArrayList<BookedMovie> {
        val gson = Gson()
        val json: String = data
        val type: Type = object : TypeToken<ArrayList<BookedMovie>>() {}.type
        return gson.fromJson(json, type)
    }

}