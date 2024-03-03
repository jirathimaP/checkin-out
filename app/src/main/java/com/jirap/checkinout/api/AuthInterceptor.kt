package com.jirap.checkinout.api

import android.content.Context
import com.jirap.checkinout.api.model.LogInUserData
import com.jirap.checkinout.api.model.LogInUserResponse
import com.jirap.checkinout.api.model.RefreshTokenRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback

class AuthInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)
    private val context = context

    // lateinit var responseRefresh:LogInUserResponse
    // lateinit var presenter: RefreshPresenter
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val accessToken = sessionManager.fetchAuthToken()
        val refreshToken = sessionManager.fetchRefreshToken()
        val username = sessionManager.getUsername()

        if (sessionManager.isAccessTokenExpired()) {
        val refreshTokenRequest = RefreshTokenRequest(
            username = username,
            refreshToken = refreshToken
        )
        var responseRefresh: LogInUserData
        runBlocking {
            ServiceLoginBuilder.buildService(context, ApiServices::class.java)
                .refreshAccessToken(refreshTokenRequest)
                .enqueue(object : Callback<LogInUserResponse> {
                    override fun onResponse(
                        call: Call<LogInUserResponse>?,
                        response: retrofit2.Response<LogInUserResponse>?
                    ) {
                        if (response!!.isSuccessful) {
                            val result = response.body()
                            if (result != null) {
                                if (result.success) {
                                    responseRefresh = result.data
                                    val expireTime = System.currentTimeMillis() + 86400 * 1000
                                    sessionManager.saveAccessToken(
                                        responseRefresh,
                                        sessionManager.getUsername(),
                                        expireTime,
                                        false
                                    )

                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<LogInUserResponse>?, t: Throwable?) {
                        System.out.println("")
                    }
                })
        }
        val newRequest = originalRequest.newBuilder()
            .header(
                "Authorization",
                "Bearer ${sessionManager.fetchAuthToken()}"
            )
            .build()

        return chain.proceed(newRequest)
         } else {
             val authorizedRequest = originalRequest.newBuilder()
                 .header("Authorization", "Bearer $accessToken")
                 .build()
             return chain.proceed(authorizedRequest)
         }
    }
}