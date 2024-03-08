package com.jirap.checkinout

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    private val locationPermissionCode = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        presenter = RefreshPresenter(this)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        if (hasReadLocationPermission()){
            goNext()
        } else {
            requestPermission()
        }

    }
    private fun goNext(){
        val sessionManager = SessionManager(this)
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
    private fun hasReadLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            locationPermissionCode
        )
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
    }override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
            goNext()
        }
    }
}