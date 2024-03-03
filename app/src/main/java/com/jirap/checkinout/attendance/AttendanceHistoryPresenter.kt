package com.jirap.checkinout.attendance

import android.content.Context
import com.jirap.checkinout.api.ApiServices
import com.jirap.checkinout.api.ServiceBuilder
import com.jirap.checkinout.api.model.AttendanceRequest
import com.jirap.checkinout.api.model.AttendanceResponse
import com.jirap.checkinout.api.model.AttendanceResponseHistory
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AttendanceHistoryPresenter(private var view: AttendanceHistoryContractor.View) : AttendanceHistoryContractor.Presenter {

    override fun attendanceHistory(context: Context, attendanceRequest: AttendanceRequest) {
        ServiceBuilder.buildService(context,ApiServices::class.java).attendanceHistory(attendanceRequest)
            .enqueue(object : Callback<AttendanceResponseHistory> {

                override fun onResponse(
                    call: Call<AttendanceResponseHistory>?,
                    response: Response<AttendanceResponseHistory>?
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

                override fun onFailure(call: Call<AttendanceResponseHistory>?, t: Throwable?) {
                    view.fail("เกิดข้อผิดพลาด รบกวนตรวจสอบอีกครั้ง")
                }
            })
    }


}