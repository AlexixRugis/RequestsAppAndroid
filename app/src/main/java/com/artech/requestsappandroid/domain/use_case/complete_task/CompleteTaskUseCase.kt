package com.artech.requestsappandroid.domain.use_case.complete_task

import com.artech.requestsappandroid.common.ActionStatus
import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.dto.RepairTask
import com.artech.requestsappandroid.domain.models.AccountData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CompleteTaskUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(id: Int): Flow<ActionStatus> = flow {
        try {
            emit(ActionStatus.Loading)
            val response = repository.completeTask(id)

            if (response.isSuccessful) {
                emit(ActionStatus.Success)
            } else {
                emit(ActionStatus.Failure)
            }
        } catch (e: HttpException) {
            emit(ActionStatus.Failure)
        } catch (e: IOException) {
            emit(ActionStatus.Failure)
        }
    }
}