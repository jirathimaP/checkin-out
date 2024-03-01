package com.jirap.checkinout.reqattendance

import android.content.Context
import com.jirap.checkinout.api.model.RequestAttendanceHistoryResponse
import com.jirap.checkinout.api.model.RequestAttendanceRequest
import com.jirap.checkinout.api.model.RequestAttendanceResponse

class ReqAttendanceHistoryContractor {
    interface View {
        fun success(requestAttendanceResponse: RequestAttendanceHistoryResponse)
        fun fail(responseMessage: String?)
    }

    interface Presenter {
        fun requestAttendanceHistory(context: Context, requestAttendanceRequest: RequestAttendanceRequest)
    }
}