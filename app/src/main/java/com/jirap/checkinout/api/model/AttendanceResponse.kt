package com.jirap.checkinout.api.model

data class AttendanceResponse(
    val error: String,
    val success: Boolean,
    val message: String,
    val data: AttendanceData
)