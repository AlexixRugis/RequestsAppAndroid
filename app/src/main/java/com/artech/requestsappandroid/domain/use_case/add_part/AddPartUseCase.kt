package com.artech.requestsappandroid.domain.use_case.add_part

import com.artech.requestsappandroid.common.ActionStatus
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.dto.AddPart
import com.artech.requestsappandroid.data.remote.dto.AddParts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddPartUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(taskId: Int, partId: Int, amount: Int): Flow<ActionStatus> = flow {
        try {
            emit(ActionStatus.Loading)
            val response = repository.addParts(taskId, AddParts(
                listOf(
                    AddPart(partId, amount)
                )
            ))

            if (response.isSuccessful) {
                emit(ActionStatus.Success)
            } else {
                emit(ActionStatus.Failure)
            }
        }
        catch (e: HttpException) {
            emit(ActionStatus.Failure)
        } catch (e: IOException) {
            emit(ActionStatus.Failure)
        }
    }
}