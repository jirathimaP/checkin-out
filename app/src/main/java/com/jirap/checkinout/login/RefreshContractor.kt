package com.jirap.checkinout.login

import android.content.Context
import com.jirap.checkinout.api.model.LogInUserRequest
import com.jirap.checkinout.api.model.LogInUserResponse
import com.jirap.checkinout.api.model.RefreshTokenRequest

class RefreshTokenContractor {
    interface View {
        fun success(loginResponse: LogInUserResponse)
        fun fail(responseMessage: String?)
    }

    interface Presenter {
        fun refreshToken(context: Context, refreshRequest: RefreshTokenRequest)
    }
}