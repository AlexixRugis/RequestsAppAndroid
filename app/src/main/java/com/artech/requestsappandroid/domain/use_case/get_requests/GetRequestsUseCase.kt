package com.artech.requestsappandroid.domain.use_case.get_request

import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.dto.RepairRequest
import com.artech.requestsappandroid.data.remote.dto.RepairRequests
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

// Вариант использования: Получить данные о заявках

class GetRequestsUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(): Flow<Resource<RepairRequests>> = flow {
        try {
            emit(Resource.Loading<RepairRequests>())
            val response = repository.getAllAvailableRequests()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error<RepairRequests>("Ошибка при получении данных с сервера"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<RepairRequests>("Ошибка при получении данных с сервера"))
        } catch (e: IOException) {
            emit(Resource.Error<RepairRequests>("Ошибка подключения к серверу"))
        }
    }
}