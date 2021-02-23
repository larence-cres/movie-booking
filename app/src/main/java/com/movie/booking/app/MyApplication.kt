package com.movie.booking.app

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.facebook.stetho.Stetho
import com.movie.booking.BuildConfig
import com.movie.booking.app.dagger.AppComponent
import com.movie.booking.app.dagger.DaggerAppComponent
import com.movie.booking.app.dagger.modules.AppModule
import com.movie.booking.appUtils.AppConstants
import timber.log.Timber

/**
 * Created by Larence on 10/29/2020.
 */

class MyApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, AppConstants.LOG_TAG, message, t)
                }
            })
            Stetho.initializeWithDefaults(this)
        }

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun get(activity: Activity): MyApplication {
        return activity.application as MyApplication
    }

    fun appComponent(): AppComponent? {
        return appComponent
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}