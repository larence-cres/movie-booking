package com.movie.booking.app.dagger.modules

import android.app.Application
import android.content.Context
import com.movie.booking.app.dagger.AppScope
import dagger.Module
import dagger.Provides

/**
 * Created by Larence on 10/29/2020.
 */

@Module
open class AppModule(application: Application) {

  private val context: Context = application.applicationContext

  @AppScope
  @Provides
  fun context(): Context {
    return context
  }

}
