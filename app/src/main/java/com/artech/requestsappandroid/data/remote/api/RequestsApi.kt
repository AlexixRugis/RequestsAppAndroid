package com.artech.requestsappandroid.data.remote.api

import com.artech.requestsappandroid.data.remote.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RequestsApi {

    @POST("login/")
    suspend fun login(@Body body: AuthenticationData): Response<ResponseDetails>

    @POST("logout/")
    suspend fun logout(): Response<ResponseDetails>

    @GET("account/")
    suspend fun getAccount(): Response<Employee>

    @GET("is_authenticated/")
    suspend fun isAuthenticated() : Response<AuthenticationStatus>

    @GET("requests/")
    suspend fun requests() : Response<RepairRequests>
}