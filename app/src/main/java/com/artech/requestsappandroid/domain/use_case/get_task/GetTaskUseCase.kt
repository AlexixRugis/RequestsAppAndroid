package com.artech.requestsappandroid.domain.use_case.get_task

import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.dto.RepairTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(id: Int): Flow<Resource<RepairTask>> = flow {
        try {
            emit(Resource.Loading<RepairTask>())
            val response = repository.getTask(id)

            if (response.isSuccessful) {
                emit(Resource.Success<RepairTask>(
                    response.body()!!
                ))
            } else {
                emit(Resource.Error<RepairTask>("Ошибка при получении данных с сервера"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<RepairTask>("Ошибка при получении данных с сервера"))
        } catch (e: IOException) {
            emit(Resource.Error<RepairTask>("Ошибка подключения к серверу"))
        }
    }
}