package com.jirap.checkinout.api.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class AttendanceRequest(
    val operation: String,
    val checkType: String,
    val latitudeIn: Double,
    val longitudeIn: Double,
    val latitudeOut: Double,
    val longitudeOut: Double,
    val ipAddress: String,
    val status: String,
    val typeJob: String,
  //  val workShift: String,
    val event: String
)