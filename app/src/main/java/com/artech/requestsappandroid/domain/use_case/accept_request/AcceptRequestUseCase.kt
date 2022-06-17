package com.artech.requestsappandroid.domain.use_case.accept_request

import com.artech.requestsappandroid.common.ActionStatus
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AcceptRequestUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(id: Int): Flow<ActionStatus> = flow {
        try {
            emit(ActionStatus.Loading)
            val response = repository.acceptRequest(id)
            if (response.isSuccessful) {
                emit(ActionStatus.Success)
            } else {
                emit(ActionStatus.Success)
            }
        } catch (e: HttpException) {
            emit(ActionStatus.Failure)
        } catch (e: IOException) {
            emit(ActionStatus.Failure)
        }
    }
}