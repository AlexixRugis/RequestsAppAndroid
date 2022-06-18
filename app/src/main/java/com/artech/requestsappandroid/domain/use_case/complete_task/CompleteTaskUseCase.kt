package com.artech.requestsappandroid.domain.use_case.complete_task

import android.net.Uri
import com.artech.requestsappandroid.common.ActionStatus
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CompleteTaskUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(id: Int, uri: Uri): Flow<ActionStatus> = flow {
        //TODO: add photo
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