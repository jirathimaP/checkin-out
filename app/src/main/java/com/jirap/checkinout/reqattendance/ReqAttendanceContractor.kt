package com.jirap.checkinout.reqattendance

import android.content.Context
import com.jirap.checkinout.api.model.RequestAttendanceRequest
import com.jirap.checkinout.api.model.RequestAttendanceResponse

class ReqAttendanceContractor {
    interface View {
        fun success(requestAttendanceResponse: RequestAttendanceResponse)
        fun fail(responseMessage: String?)
    }

    interface Presenter {
        fun requestAttendance(context: Context, requestAttendanceRequest: RequestAttendanceRequest)
    }
}