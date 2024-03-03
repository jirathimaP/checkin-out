package com.jirap.checkinout.login

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jirap.checkinout.MainActivity
import com.jirap.checkinout.R
import com.jirap.checkinout.api.SessionManager
import com.jirap.checkinout.api.model.LogInUserRequest
import com.jirap.checkinout.api.model.LogInUserResponse
import com.jirap.checkinout.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(),LoginContractor.View {

    private lateinit var binding: ActivityLoginBinding
    lateinit var presenter: LoginPresenter

    private lateinit var stUsername: String
    private lateinit var stPassword: String
    private lateinit var loading: ProgressBar
    private lateinit var icShowPassword: ImageView
    private lateinit var username: EditText
    private lateinit var password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val login = binding.login
        loading = binding.loading
        presenter = LoginPresenter(this)
        loading.visibility = View.GONE
        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            requestLogin()
        }
        username = binding.username
        password = binding.password
        icShowPassword = binding.showPassBtn
        icShowPassword.setOnClickListener{
            if(password.transformationMethod.equals(PasswordTransformationMethod.getInstance())){
                password.transformationMethod = HideReturnsTransformationMethod.getInstance();
                icShowPassword.setImageResource(R.drawable.hide)
            } else{
                password.transformationMethod = PasswordTransformationMethod.getInstance();
                icShowPassword.setImageResource(R.drawable.view)

            }
        }
    }

    private fun requestLogin() {
        stUsername = binding.username.text.toString()
        stPassword = binding.password.text.toString()

        if (stUsername.isNotEmpty() && stPassword.isNotEmpty()) {
            val loginRequest = LogInUserRequest(
                username = stUsername,
                password = stPassword
            )
            presenter.login(this,loginRequest)
        } else {
            loading.visibility = View.GONE
            Toast.makeText(applicationContext, "กรุณาระบุ username และ password", Toast.LENGTH_SHORT).show()
        }
    }

    override fun success(loginResponse: LogInUserResponse) {
        val expireTime = System.currentTimeMillis() +  loginResponse.data.expires_in * 1000
        val sessionManager = SessionManager(this)
        sessionManager.saveAccessToken(loginResponse.data,stUsername,expireTime,true)
        loading.visibility = View.GONE
        goToMainActivity()
    }

    override fun fail(responseMessage: String?) {
        loading.visibility = View.GONE
        Toast.makeText(applicationContext, responseMessage, Toast.LENGTH_SHORT).show()
    }
    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}