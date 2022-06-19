package com.artech.requestsappandroid.domain.use_case.change_password

import androidx.lifecycle.ViewModel
import com.artech.requestsappandroid.common.ActionStatus
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.dto.ChangePasswordData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

// Вариант использования: Сменить пароль

class ChangePasswordUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(password: String): Flow<ActionStatus> = flow {
        try {
            emit(ActionStatus.Loading)
            val response = repository.changePassword(ChangePasswordData(password))

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