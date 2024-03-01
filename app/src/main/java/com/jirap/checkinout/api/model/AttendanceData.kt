package com.jirap.checkinout.api.model

import java.util.Date

data class AttendanceData(
    val event: String,
    val ipAddress: String,
    val branchNo: String,
    val typeJob: String,
    val branchName: String,
    val status: String,
    val workShift: String,
    val date: String,
    val longitudeIn: String,
    val firstname: String,
    val updateBy: String,
    val longitudeOut: String,
    val updateDate: String,
    val latitudeIn: String,
    val late: String,
    val latitudeOut: String,
    val createBy: String,
    val lastname: String,
    val createDate: String,
    val checkIn: String,
    val month: String,
    val checkOut: String,
    val verify: String,
    val empNo: String
)