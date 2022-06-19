package com.artech.requestsappandroid.domain.use_case.complete_task

import android.content.Context
import android.net.Uri
import com.artech.requestsappandroid.common.ActionStatus
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.utils.UriUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

// Вариант использования: Завершить заявку

class CompleteTaskUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    operator fun invoke(id: Int, context: Context, uri: Uri): Flow<ActionStatus> = flow {
        try {
            emit(ActionStatus.Loading)

            val file = File(UriUtils.getPathFromUri(context, uri))
            val requestFile: RequestBody =
                file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

            val body: MultipartBody.Part = MultipartBody.Part.createFormData("image", file.name, requestFile)

            val response = repository.completeTask(id, body)

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