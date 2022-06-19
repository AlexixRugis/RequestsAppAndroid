package com.artech.requestsappandroid.data.remote.api

import com.artech.requestsappandroid.data.remote.dto.AddParts
import com.artech.requestsappandroid.data.remote.dto.AuthenticationData
import com.artech.requestsappandroid.data.remote.dto.ChangePasswordData
import okhttp3.MultipartBody
import javax.inject.Inject

// Репозиторий для данных

class ApiRepository @Inject constructor(private val api: RequestsApi){
    suspend fun logout() = api.logout()
    suspend fun login(data: AuthenticationData) = api.login(data)
    suspend fun getAccount() = api.getAccount()
    suspend fun changePassword(data: ChangePasswordData) = api.changePassword(data)
    suspend fun isAuthenticated() = api.isAuthenticated()
    suspend fun getAllAvailableRequests() = api.requests();
    suspend fun getRequest(id: Int) = api.getRequest(id)
    suspend fun acceptRequest(id: Int) = api.acceptRequest(id)
    suspend fun getTasks() = api.getTasks()
    suspend fun getTask(id: Int) = api.getTask(id)
    suspend fun completeTask(id: Int, image: MultipartBody.Part) = api.completeTask(id, image)
    suspend fun getRepairParts(searchQuery: String) = api.getRepairParts(searchQuery)
    suspend fun addParts(id: Int, partRequests: AddParts) = api.addParts(id, partRequests)
}