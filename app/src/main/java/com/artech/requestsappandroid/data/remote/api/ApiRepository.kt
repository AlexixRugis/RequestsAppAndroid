package com.artech.requestsappandroid.data.remote.api

import com.artech.requestsappandroid.data.remote.models.AuthenticationData
import javax.inject.Inject

class ApiRepository @Inject constructor(private val api: RequestsApi){
    suspend fun logout() = api.logout()
    suspend fun login(data: AuthenticationData) = api.login(data)
    suspend fun getAccount() = api.getAccount()
    suspend fun isAuthenticated() = api.isAuthenticated()
    suspend fun getAllAvailableRequests() = api.requests();
}