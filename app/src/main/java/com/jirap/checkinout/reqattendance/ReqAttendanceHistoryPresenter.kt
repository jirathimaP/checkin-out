package com.jirap.checkinout.reqattendance

import android.content.Context
import com.jirap.checkinout.api.ApiServices
import com.jirap.checkinout.api.ServiceBuilder
import com.jirap.checkinout.api.model.RequestAttendanceHistoryResponse
import com.jirap.checkinout.api.model.RequestAttendanceRequest
import com.jirap.checkinout.api.model.RequestAttendanceResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReqAttendanceHistoryPresenter(private var view: ReqAttendanceHistoryContractor.View) : ReqAttendanceHistoryContractor.Presenter {

    override fun requestAttendanceHistory(context: Context, requestAttendanceRequest: RequestAttendanceRequest) {
        ServiceBuilder.buildService(context,ApiServices::class.java).requestAttendanceHistory(requestAttendanceRequest)
            .enqueue(object : Callback<RequestAttendanceHistoryResponse> {

                override fun onResponse(
                    call: Call<RequestAttendanceHistoryResponse>?,
                    response: Response<RequestAttendanceHistoryResponse>?
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

                override fun onFailure(call: Call<RequestAttendanceHistoryResponse>?, t: Throwable?) {
                    view.fail("เกิดข้อผิดพลาด รบกวนตรวจสอบอีกครั้ง")
                }
            })
    }
}