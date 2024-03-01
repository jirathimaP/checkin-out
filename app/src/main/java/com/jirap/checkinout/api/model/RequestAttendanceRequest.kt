package com.jirap.checkinout.api.model

data class RequestAttendanceRequest(
    val empNo: String,
    val reason: String,
    val date: String,
    val checkIn: String,
    val checkOut: String,
    val operation: String,
    val month: String
)