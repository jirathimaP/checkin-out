package com.jirap.checkinout.api

import android.content.Context
import android.content.SharedPreferences
import com.jirap.checkinout.api.model.LogInUserResponse

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)

    fun isAccessTokenExpired(): Boolean {
        val expirationTime = prefs.getLong("expiresIn", 0)
        val currentTimeMillis = System.currentTimeMillis()
        return currentTimeMillis >= expirationTime
    }

    fun fetchAuthToken():String {
        return prefs.getString("accessToken", null)!!
    }

    fun fetchRefreshToken():String {
        return prefs.getString("refreshToken", null)!!
    }

    fun getUsername():String {
        return prefs.getString("username", null)!!
    }

    fun isRefresh():Boolean {
        return prefs.getBoolean("isRefresh", false)!!
    }

    fun saveAccessToken(response: LogInUserResponse,username: String,expireTime: Long,isRefresh : Boolean) {
        val editor = prefs.edit()
        editor.putString("username", username)
        editor.putString("accessToken", response.data.access_token)
        editor.putLong("expiresIn",expireTime)
        editor.putString("refreshToken",response.data.refresh_token)
        editor.putBoolean("isRefresh",isRefresh)
        editor.apply()
    }
}