package com.jirap.checkinout.api

import android.content.Context
import com.jirap.checkinout.api.model.LogInUserResponse
import com.jirap.checkinout.api.model.RefreshTokenRequest
import com.jirap.checkinout.login.RefreshPresenter
import com.jirap.checkinout.login.RefreshTokenContractor
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)
    private val context = context

   // lateinit var presenter: RefreshPresenter
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val accessToken = sessionManager.fetchAuthToken()
        val username = sessionManager.fetchRefreshToken()

        if (sessionManager.isAccessTokenExpired()) {
            val refreshTokenRequest = RefreshTokenRequest(
                username = username,
                refreshToken = sessionManager.getUsername()
            )
            val response = ServiceLoginBuilder.buildService(context, ApiServices::class.java)
                .refresh(refreshTokenRequest)
            val expireTime = System.currentTimeMillis() + 86400 * 1000
            sessionManager.saveAccessToken(response,sessionManager.getUsername(),expireTime,false)
            val newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer ${response.data.access_token}")
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