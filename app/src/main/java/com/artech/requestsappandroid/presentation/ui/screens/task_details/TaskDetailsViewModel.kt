package com.artech.requestsappandroid.presentation.ui.screens.task_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artech.requestsappandroid.common.ActionStatus
import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.domain.use_case.complete_task.CompleteTaskUseCase
import com.artech.requestsappandroid.domain.use_case.get_task.GetTaskUseCase
import com.artech.requestsappandroid.presentation.ui.screens.task_details.models.TaskDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val getTaskUseCase: GetTaskUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _id : Int = -1

    private val _state = MutableStateFlow(TaskDetailsState())
    val state: StateFlow<TaskDetailsState> = _state

    init {
        _id = savedStateHandle.get<String>("taskId")!!.toInt()
    }

    fun initialize() {
        loadTask()
    }

    private fun loadTask() {
        viewModelScope.launch {
            getTaskUseCase.invoke(_id).collect {
                when (it) {
                    is Resource.Loading -> _state.value = TaskDetailsState(isLoading = true)
                    is Resource.Success -> _state.value = TaskDetailsState(task = it.data)
                    is Resource.Error -> _state.value = TaskDetailsState(error = it.message!!)
                }
            }
        }
    }
}