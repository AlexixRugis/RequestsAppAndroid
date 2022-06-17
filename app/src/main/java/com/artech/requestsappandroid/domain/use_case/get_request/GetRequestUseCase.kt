package com.artech.requestsappandroid.domain.use_case.get_request

import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.dto.RepairRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRequestUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(id : Int): Flow<Resource<RepairRequest>> = flow {
        try {
            emit(Resource.Loading<RepairRequest>())
            val response = repository.getRequest(id)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error<RepairRequest>("Ошибка при получении данных с сервера"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<RepairRequest>("Ошибка при получении данных с сервера"))
        } catch (e: IOException) {
            emit(Resource.Error<RepairRequest>("Ошибка подключения к серверу"))
        }
    }
}