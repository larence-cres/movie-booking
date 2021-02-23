package com.movie.booking.app.dagger.modules

import android.content.Context
import com.movie.booking.app.dagger.AppScope
import com.movie.booking.appUtils.SharedPreferenceUtils
import dagger.Module
import dagger.Provides

/**
 * Created by Larence on 10/29/2020.
 */

@Module
open class SharedPreferenceModule {

    @AppScope
    @Provides
    fun preferencesManager(context: Context): SharedPreferenceUtils {
        return SharedPreferenceUtils(context)
    }

}
