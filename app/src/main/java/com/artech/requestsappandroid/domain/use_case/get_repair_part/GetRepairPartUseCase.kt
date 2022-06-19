package com.artech.requestsappandroid.domain.use_case.get_repair_part

import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.dto.RepairPart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRepairPartUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(id: Int): Flow<Resource<RepairPart>> = flow {
        try {
            emit(Resource.Loading<RepairPart>())
            val response = repository.getRepairParts("")

            if (response.isSuccessful) {
                emit(Resource.Success<RepairPart>(response.body()!!.first { it.id == id }))
            } else {
                emit(Resource.Error<RepairPart>("Ошибка при получении данных с сервера"))
            }
        } catch (e: NoSuchElementException) {
            emit(Resource.Error<RepairPart>("Элемент не найден"))
        }
        catch (e: HttpException) {
            emit(Resource.Error<RepairPart>("Ошибка при получении данных с сервера"))
        } catch (e: IOException) {
            emit(Resource.Error<RepairPart>("Ошибка подключения к серверу"))
        }
    }
}