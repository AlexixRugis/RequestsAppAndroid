package com.artech.requestsappandroid.domain.use_case.get_account_data

import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.dto.Employee
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAccountDataUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(): Flow<Resource<Employee>> = flow {
        try {
            emit(Resource.Loading<Employee>())
            val employeeResponse = repository.getAccount()

            if (employeeResponse.isSuccessful) {
                emit(Resource.Success<Employee>(employeeResponse.body()!!,
                ))
            } else {
                emit(Resource.Error<Employee>("Ошибка при получении данных с сервера"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<Employee>("Ошибка при получении данных с сервера"))
        } catch (e: IOException) {
            emit(Resource.Error<Employee>("Ошибка подключения к серверу"))
        }
    }
}