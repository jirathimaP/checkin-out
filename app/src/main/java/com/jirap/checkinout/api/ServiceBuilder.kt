package com.jirap.checkinout.api

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ServiceBuilder {
    var apiBaseUrl: String = "https://3c0m4m6rw3.execute-api.ap-southeast-1.amazonaws.com/prod-hr/"

    fun <S> buildService(context: Context,serviceClass: Class<S>?): S {
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.MINUTES)
            .writeTimeout(30, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.MINUTES)
            .addInterceptor(AuthInterceptor(context)).build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //val retrofit = builder.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }
}
