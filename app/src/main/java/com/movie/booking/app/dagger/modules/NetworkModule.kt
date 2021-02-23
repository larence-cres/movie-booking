package com.movie.booking.app.dagger.modules

import android.content.Context
import com.movie.booking.api.ApiConstants
import com.movie.booking.api.ApiUrls
import com.movie.booking.api.WebService
import com.movie.booking.app.dagger.AppScope
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Larence on 10/29/2020.
 */

@Module
open class NetworkModule {

    @AppScope
    @Provides
    fun cache(context: Context): Cache {
        return Cache(File(context.cacheDir, ApiConstants.HTTP_DIR_CACHE), ApiConstants.CACHE_SIZE.toLong())
    }

    @AppScope
    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message -> Timber.i(message) }
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @AppScope
    @Provides
    fun okHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(TokenInterceptor())
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }

    @AppScope
    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
            override fun shouldSkipField(f: FieldAttributes): Boolean {
                return false
            }

            override fun shouldSkipClass(clazz: Class<*>): Boolean {
                return false
            }
        }).create()
        return Retrofit.Builder().client(okHttpClient)
            .baseUrl(ApiUrls.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @AppScope
    @Provides
    fun webservice(retrofit: Retrofit): WebService {
        return retrofit.create(WebService::class.java)
    }

}