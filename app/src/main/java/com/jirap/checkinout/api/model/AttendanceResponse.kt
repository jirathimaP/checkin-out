package com.jirap.checkinout.api.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class AttendanceResponse(
    val error: String,
    val success: Boolean,
    val message: String,
    val data: List<AttendanceData>
)