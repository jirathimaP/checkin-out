package com.jirap.checkinout.attendance

import android.content.Context
import com.jirap.checkinout.api.model.AttendanceRequest
import com.jirap.checkinout.api.model.AttendanceResponse
import com.jirap.checkinout.api.model.AttendanceResponseHistory

class AttendanceHistoryContractor {
    interface View {
        fun success(attendanceResponse: AttendanceResponseHistory)
        fun fail(responseMessage: String?)
    }

    interface Presenter {
        fun attendanceHistory(context: Context, attendanceRequest: AttendanceRequest)
    }
}