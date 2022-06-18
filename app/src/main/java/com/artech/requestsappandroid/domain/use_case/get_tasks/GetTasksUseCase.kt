package com.artech.requestsappandroid.domain.use_case.get_tasks

import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.dto.RepairTasks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(): Flow<Resource<RepairTasks>> = flow {
        try {
            emit(Resource.Loading<RepairTasks>())
            val response = repository.getTasks()

            if (response.isSuccessful) {
                emit(Resource.Success<RepairTasks>(
                    response.body()!!
                ))
            } else {
                emit(Resource.Error<RepairTasks>("Ошибка при получении данных с сервера"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<RepairTasks>("Ошибка при получении данных с сервера"))
        } catch (e: IOException) {
            emit(Resource.Error<RepairTasks>("Ошибка подключения к серверу"))
        }
    }
}