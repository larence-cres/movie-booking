package com.movie.booking.app.dagger

import android.content.Context
import com.movie.booking.api.WebService
import com.movie.booking.app.dagger.modules.AppModule
import com.movie.booking.app.dagger.modules.NetworkModule
import com.movie.booking.app.dagger.modules.SharedPreferenceModule
import com.movie.booking.appUtils.SharedPreferenceUtils
import dagger.Component
import okhttp3.OkHttpClient

/**
 * Created by Larence on 10/29/2020.
 */

@AppScope
@Component(modules = [(AppModule::class), (NetworkModule::class), (SharedPreferenceModule::class)])
interface AppComponent {

  fun context(): Context

  fun okHttpClient(): OkHttpClient

  fun webservice(): WebService

  fun sharedPreferenceUtils(): SharedPreferenceUtils

}
