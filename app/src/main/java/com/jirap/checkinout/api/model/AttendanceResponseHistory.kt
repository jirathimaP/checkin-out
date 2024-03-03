package com.jirap.checkinout.api.model

data class AttendanceResponseHistory(
    val error: String,
    val success: Boolean,
    val message: String,
    val data: List<AttendanceData>
)