package com.jirap.checkinout.api.model

data class LogInUserResponse(
    val message: String,
    val error: String,
    val success: Boolean,
    val data: LogInUserData
)