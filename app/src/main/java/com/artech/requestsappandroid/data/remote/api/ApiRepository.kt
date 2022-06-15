package com.artech.requestsappandroid.data.remote.api

import javax.inject.Inject

class ApiRepository @Inject constructor(private val api: RequestsApi){
    suspend fun isAuthenticated() = api.isAuthenticated()
    suspend fun getAllAvailableRequests() = api.requests();
}