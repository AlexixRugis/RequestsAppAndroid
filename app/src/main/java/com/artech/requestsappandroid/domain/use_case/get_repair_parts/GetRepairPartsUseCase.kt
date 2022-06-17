package com.artech.requestsappandroid.domain.use_case.get_repair_parts

import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.dto.Part
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRepairPartsUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(): Flow<Resource<List<Part>>> = flow {
        try {
            emit(Resource.Loading<List<Part>>())
            val response = repository.getRepairParts()

            if (response.isSuccessful) {
                emit(Resource.Success<List<Part>>(response.body()!!))
            } else {
                emit(Resource.Error<List<Part>>("Ошибка при получении данных с сервера"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<List<Part>>("Ошибка при получении данных с сервера"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Part>>("Ошибка подключения к серверу"))
        }
    }
}