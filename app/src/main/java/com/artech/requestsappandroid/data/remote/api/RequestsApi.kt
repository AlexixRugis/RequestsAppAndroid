package com.artech.requestsappandroid.data.remote.api

import com.artech.requestsappandroid.data.remote.models.AuthenticationStatus
import com.artech.requestsappandroid.data.remote.models.RepairRequests
import retrofit2.Response
import retrofit2.http.GET

interface RequestsApi {

    @GET("is_authenticated/")
    suspend fun isAuthenticated() : Response<AuthenticationStatus>

    @GET("requests/")
    suspend fun requests() : Response<RepairRequests>
}