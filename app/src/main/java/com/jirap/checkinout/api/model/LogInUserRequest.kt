package com.jirap.checkinout.api.model

import com.google.gson.annotations.SerializedName

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LogInUserRequest(
    @SerializedName("username") val username: String?,
    @SerializedName("password") val password: String?
)