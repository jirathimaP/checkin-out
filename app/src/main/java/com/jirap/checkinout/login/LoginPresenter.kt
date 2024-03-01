package com.jirap.checkinout.login

import android.content.Context
import com.jirap.checkinout.api.ApiServices
import com.jirap.checkinout.api.ServiceBuilder
import com.jirap.checkinout.api.ServiceLoginBuilder
import com.jirap.checkinout.api.model.LogInUserRequest
import com.jirap.checkinout.api.model.LogInUserResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginPresenter(private var view: LoginContractor.View) : LoginContractor.Presenter {

    override fun login(context: Context, loginRequest: LogInUserRequest) {
        ServiceLoginBuilder.buildService(context,ApiServices::class.java).login(loginRequest)
            .enqueue(object : Callback<LogInUserResponse> {

                override fun onResponse(
                    call: Call<LogInUserResponse>?,
                    response: Response<LogInUserResponse>?
                ) {
                    if (response!!.isSuccessful) {
                        val result = response.body()
                        if (result != null) {
                            if (result.success) {
                                view.success(result)
                            } else {
                                view.fail(result.message)
                            }
                        }
                    } else {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            view.fail(jObjError.getString("message"))
                        } catch (e: Exception) {
                            view.fail("เกิดข้อผิดพลาด รบกวนตรวจสอบอีกครั้ง")
                        }
                    }
                }

                override fun onFailure(call: Call<LogInUserResponse>?, t: Throwable?) {
                    view.fail("เกิดข้อผิดพลาด รบกวนตรวจสอบอีกครั้ง")
                }
            })
    }


}