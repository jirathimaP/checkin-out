package com.jirap.checkinout.login

import android.content.Context
import com.jirap.checkinout.api.model.LogInUserRequest
import com.jirap.checkinout.api.model.LogInUserResponse

class LoginContractor {
    interface View {
        fun success(loginResponse: LogInUserResponse)
        fun fail(responseMessage: String?)
    }

    interface Presenter {
        fun login(context: Context, loginRequest: LogInUserRequest)
    }
}