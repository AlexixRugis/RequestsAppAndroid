package com.artech.requestsappandroid.domain.use_case.get_account_data

import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.domain.models.AccountData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAccountDataUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(): Flow<Resource<AccountData>> = flow {
        try {
            emit(Resource.Loading<AccountData>())
            val employeeResponse = repository.getAccount()
            val tasksResponse = repository.getTasks()

            if (employeeResponse.isSuccessful && tasksResponse.isSuccessful) {
                emit(Resource.Success<AccountData>(
                    AccountData(
                        employeeResponse.body()!!,
                        tasksResponse.body()!!
                    )
                ))
            } else {
                emit(Resource.Error<AccountData>("Ошибка при получении данных с сервера"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<AccountData>("Ошибка при получении данных с сервера"))
        } catch (e: IOException) {
            emit(Resource.Error<AccountData>("Ошибка подключения к серверу"))
        }
    }
}