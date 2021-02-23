package com.movie.booking.appUtils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Larence on 10/29/2020.
 */

class SharedPreferenceUtils(context: Context) {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AppConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE)

    fun save(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun saveInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getDefaultSharedPreference(): SharedPreferences {
        return sharedPreferences
    }

    operator fun get(key: String): String {
        return sharedPreferences.getString(key, "")
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun isEmpty(key: String): Boolean {
        return sharedPreferences.getString(key, null) == null
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}