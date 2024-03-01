package com.jirap.checkinout.reqattendance

import android.content.Context
import com.jirap.checkinout.api.ApiServices
import com.jirap.checkinout.api.ServiceBuilder
import com.jirap.checkinout.api.model.RequestAttendanceRequest
import com.jirap.checkinout.api.model.RequestAttendanceResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReqAttendancePresenter(private var view: ReqAttendanceContractor.View) : ReqAttendanceContractor.Presenter {

    override fun requestAttendance(context: Context, requestAttendanceRequest: RequestAttendanceRequest) {
        ServiceBuilder.buildService(context,ApiServices::class.java).requestAttendance(requestAttendanceRequest)
            .enqueue(object : Callback<RequestAttendanceResponse> {

                override fun onResponse(
                    call: Call<RequestAttendanceResponse>?,
                    response: Response<RequestAttendanceResponse>?
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

                override fun onFailure(call: Call<RequestAttendanceResponse>?, t: Throwable?) {
                    view.fail("เกิดข้อผิดพลาด รบกวนตรวจสอบอีกครั้ง")
                }
            })
    }
}