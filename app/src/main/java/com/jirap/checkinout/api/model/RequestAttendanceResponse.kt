package com.jirap.checkinout.api.model

data class RequestAttendanceResponse(
    val error: String,
    val success: Boolean,
    val message: String,
    val data: RequestAttendanceData
)