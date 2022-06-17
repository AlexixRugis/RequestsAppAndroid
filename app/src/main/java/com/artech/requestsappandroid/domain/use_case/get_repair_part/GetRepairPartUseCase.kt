package com.artech.requestsappandroid.domain.use_case.get_repair_part

import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.dto.Part
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRepairPartUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Part>> = flow {
        try {
            emit(Resource.Loading<Part>())
            val response = repository.getRepairParts()

            if (response.isSuccessful) {
                emit(Resource.Success<Part>(response.body()!!.first { it.id == id }))
            } else {
                emit(Resource.Error<Part>("Ошибка при получении данных с сервера"))
            }
        } catch (e: NoSuchElementException) {
            emit(Resource.Error<Part>("Элемент не найден"))
        }
        catch (e: HttpException) {
            emit(Resource.Error<Part>("Ошибка при получении данных с сервера"))
        } catch (e: IOException) {
            emit(Resource.Error<Part>("Ошибка подключения к серверу"))
        }
    }
}