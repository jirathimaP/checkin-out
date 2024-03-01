package com.jirap.checkinout.api.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class RefreshTokenRequest(
    val username: String,
    val refreshToken: String
)