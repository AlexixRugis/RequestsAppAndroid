package com.artech.requestsappandroid.presentation.ui.screens.complete_task

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artech.requestsappandroid.common.ActionStatus
import com.artech.requestsappandroid.domain.use_case.complete_task.CompleteTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompleteTaskViewModel @Inject constructor(
    private val completeTaskUseCase: CompleteTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _id: Int = -1

    private val _state = MutableStateFlow(CompleteTaskViewState())
    val state: StateFlow<CompleteTaskViewState> = _state

    init {
        _id = savedStateHandle.get<String>("taskId")!!.toInt()
    }

    fun submitImage(context: Context, uri: Uri) {
        viewModelScope.launch {
            completeTaskUseCase.invoke(_id, context, uri).collect {
                when (it) {
                    ActionStatus.Loading -> _state.value = CompleteTaskViewState(isLoading = true)
                    ActionStatus.Success -> _state.value = CompleteTaskViewState(isCompleted = true)
                    ActionStatus.Failure -> _state.value = CompleteTaskViewState(error = "Произошла ошибка при выполнении действия. Попробуйте позже")
                }
            }
        }
    }

}