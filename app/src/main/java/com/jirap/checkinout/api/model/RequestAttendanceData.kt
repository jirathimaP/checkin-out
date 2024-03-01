package com.jirap.checkinout.api.model

data class RequestAttendanceData(
    val updateDate: String,
    val branchNo: String,
    val branchName: String,
    val status: String,
    val reason: String,
    val lastname: String,
    val createBy: String,
    val createDate: String,
    val date: String,
    val checkIn: String,
    val month: String,
    val firstname: String,
    val updateBy: String,
    val checkOut: String,
    val empNo: String
)