package com.jirap.checkinout.api

import com.jirap.checkinout.api.model.*
import retrofit2.Call
import retrofit2.http.*


interface ApiServices {

    @POST("dev-auth/token")
    fun login(@Body data: LogInUserRequest): Call<LogInUserResponse>

    @POST("dev-auth/refresh-token")
    fun refreshAccessToken(@Body data: RefreshTokenRequest): Call<LogInUserResponse>

    @POST("dev-auth/refresh-token")
    fun refresh(@Body data: RefreshTokenRequest): LogInUserResponse

    @POST("dev-hr/attendance")
    fun attendance(@Body data: AttendanceRequest): Call<AttendanceResponse>

    @POST("dev-hr/attendance-request")
    fun requestAttendance(@Body data: RequestAttendanceRequest): Call<RequestAttendanceResponse>

    @POST("dev-hr/attendance-request")
    fun requestAttendanceHistory(@Body data: RequestAttendanceRequest): Call<RequestAttendanceHistoryResponse>
}