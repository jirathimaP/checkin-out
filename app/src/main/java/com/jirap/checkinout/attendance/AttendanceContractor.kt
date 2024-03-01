package com.jirap.checkinout.attendance

import android.content.Context
import com.jirap.checkinout.api.model.AttendanceRequest
import com.jirap.checkinout.api.model.AttendanceResponse

class AttendanceContractor {
    interface View {
        fun success(attendanceResponse: AttendanceResponse)
        fun fail(responseMessage: String?)
    }

    interface Presenter {
        fun attendance(context: Context, attendanceRequest: AttendanceRequest)
    }
}