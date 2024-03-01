package com.jirap.checkinout.api.model

data class LogInUserData(
    val id_token: String,
    val refresh_token: String,
    val access_token: String,
    val expires_in: Long,
    val token_type: String
)