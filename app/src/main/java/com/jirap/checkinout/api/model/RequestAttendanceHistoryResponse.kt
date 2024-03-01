package com.jirap.checkinout.api.model

data class RequestAttendanceHistoryResponse(
    val error: String,
    val success: Boolean,
    val message: String,
    val data: List<RequestAttendanceData>
)