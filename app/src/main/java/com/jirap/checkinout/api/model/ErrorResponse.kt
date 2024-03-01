package com.jirap.checkinout.api.model

import com.google.gson.annotations.SerializedName

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class ErrorResponse(
    @SerializedName("message") val message: String?,
    @SerializedName("error") val error: Boolean?,
    @SerializedName("success") val success: Boolean?
)