package com.jirap.checkinout.attendance

import android.content.Context
import com.jirap.checkinout.api.ApiServices
import com.jirap.checkinout.api.ServiceBuilder
import com.jirap.checkinout.api.model.AttendanceRequest
import com.jirap.checkinout.api.model.AttendanceResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AttendancePresenter(private var view: AttendanceContractor.View) : AttendanceContractor.Presenter {

    override fun attendance(context: Context, attendanceRequest: AttendanceRequest) {
        ServiceBuilder.buildService(context,ApiServices::class.java).attendance(attendanceRequest)
            .enqueue(object : Callback<AttendanceResponse> {

                override fun onResponse(
                    call: Call<AttendanceResponse>?,
                    response: Response<AttendanceResponse>?
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

                override fun onFailure(call: Call<AttendanceResponse>?, t: Throwable?) {
                    view.fail("เกิดข้อผิดพลาด รบกวนตรวจสอบอีกครั้ง")
                }
            })
    }


}