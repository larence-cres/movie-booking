package com.movie.booking.app.dagger.modules

import com.movie.booking.api.ApiConstants
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response


/**
 * Created by Larence on 10/29/2020.
 */

class TokenInterceptor : Interceptor {

    private val API_KEY_PARAM = "api_key"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newHttpUrl: HttpUrl = request.url().newBuilder()
            .setQueryParameter(API_KEY_PARAM, ApiConstants.API_KEY)
            .build()

        val token = "Bearer " + ApiConstants.ACCESS_TOKEN
        val newRequest = request.newBuilder()
            .url(newHttpUrl)
//            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json;charset=utf-8")
            .addHeader("Authorization", token)
            .build()

        return chain.proceed(newRequest)
    }

}