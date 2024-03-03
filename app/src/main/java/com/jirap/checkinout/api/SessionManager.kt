package com.jirap.checkinout.api

import android.content.Context
import android.content.SharedPreferences
import com.jirap.checkinout.api.model.LogInUserData

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)

    fun isAccessTokenExpired(): Boolean {
        val expirationTime = prefs.getLong("expiresIn", 0)
        val currentTimeMillis = System.currentTimeMillis()
        return currentTimeMillis >= expirationTime
    }

    fun fetchAuthToken():String {
        return prefs.getString("accessToken", "")!!
    }

    fun fetchRefreshToken():String {
        return prefs.getString("refreshToken", "")!!
    }

    fun getUsername():String {
        return prefs.getString("username", "")!!
    }

    fun isRefresh():Boolean {
        return prefs.getBoolean("isRefresh", false)!!
    }

    fun saveAccessToken(response: LogInUserData, username: String, expireTime: Long, isRefresh: Boolean) {
        val editor = prefs.edit()
        editor.putString("username", username)
        editor.putString("accessToken", response.access_token)
        editor.putLong("expiresIn",expireTime)
        editor.putString("refreshToken",response.refresh_token)
        editor.putBoolean("isRefresh",isRefresh)
        editor.apply()
    }
}