package com.jirap.checkinout.api

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ServiceLoginBuilder {
    var apiBaseUrl: String = "https://3r3bjkdo6a.execute-api.ap-southeast-1.amazonaws.com/prod-auth/"

    private val httpClient = OkHttpClient.Builder()

    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()

    fun <S> buildService(context: Context, serviceClass: Class<S>?): S {
        var client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .connectTimeout(30, TimeUnit.MINUTES)
            .writeTimeout(30, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.MINUTES)
            .build()

        var builder = Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))

        val retrofit = builder.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }
}
