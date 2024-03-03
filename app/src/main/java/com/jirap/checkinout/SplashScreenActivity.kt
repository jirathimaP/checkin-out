package com.jirap.checkinout

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.jirap.checkinout.api.SessionManager
import com.jirap.checkinout.api.model.LogInUserResponse
import com.jirap.checkinout.api.model.RefreshTokenRequest
import com.jirap.checkinout.login.LoginActivity
import com.jirap.checkinout.login.RefreshPresenter
import com.jirap.checkinout.login.RefreshTokenContractor


@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity(), RefreshTokenContractor.View {

    lateinit var presenter: RefreshPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        presenter = RefreshPresenter(this)
        val sessionManager = SessionManager(this)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            val isExpired = sessionManager.isAccessTokenExpired()
            if (!isExpired) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val refreshToken = sessionManager.fetchRefreshToken()
                if (refreshToken != "") {
                    val refreshTokenRequest = RefreshTokenRequest(
                        username = sessionManager.getUsername(),
                        refreshToken = refreshToken
                    )
                    presenter.refreshToken(this,refreshTokenRequest)
                } else {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 3000)
    }

    override fun success(loginResponse: LogInUserResponse) {
        val expireTime = System.currentTimeMillis() + 86400 * 1000
        val sessionManager = SessionManager(this)
        sessionManager.saveAccessToken(loginResponse.data,sessionManager.getUsername(),expireTime,false)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun fail(responseMessage: String?) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}