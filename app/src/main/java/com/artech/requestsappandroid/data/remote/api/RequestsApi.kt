package com.artech.requestsappandroid.data.remote.api

import com.artech.requestsappandroid.data.remote.dto.*
import com.artech.requestsappandroid.data.remote.dto.RepairPart
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

// Интерфейс API для взаимодействия с сервером

interface RequestsApi {

    @POST("login/")
    suspend fun login(@Body body: AuthenticationData): Response<ResponseDetails>

    @POST("logout/")
    suspend fun logout(): Response<ResponseDetails>

    @GET("account/")
    suspend fun getAccount(): Response<Employee>

    @POST("change_password/")
    suspend fun changePassword(@Body data: ChangePasswordData) : Response<ResponseDetails>

    @GET("is_authenticated/")
    suspend fun isAuthenticated() : Response<AuthenticationStatus>

    @GET("requests/")
    suspend fun requests() : Response<RepairRequests>

    @GET("requests/{id}/")
    suspend fun getRequest(@Path("id") id: Int) : Response<RepairRequest>

    @POST("requests/{id}/accept/")
    suspend fun acceptRequest(@Path("id") id: Int) : Response<ResponseDetails>

    @GET("tasks/")
    suspend fun getTasks() : Response<RepairTasks>

    @GET("tasks/{id}/")
    suspend fun getTask(@Path("id") id : Int) : Response<RepairTask>

    @Multipart
    @POST("tasks/{id}/complete/")
    suspend fun completeTask(
        @Path("id") id : Int,
        @Part image : MultipartBody.Part
    ) : Response<ResponseDetails>

    @GET("repair_parts/")
    suspend fun getRepairParts(@Query("search") searchQuery: String) : Response<List<RepairPart>>

    @POST("tasks/{id}/add_parts/")
    suspend fun addParts(@Path("id") id: Int, @Body partRequests: AddParts) : Response<ResponseDetails>
}