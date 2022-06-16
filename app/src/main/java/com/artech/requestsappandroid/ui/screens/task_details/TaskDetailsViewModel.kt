package com.artech.requestsappandroid.ui.screens.task_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.artech.requestsappandroid.common.EventHandler
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.ui.screens.task_details.models.TaskDetailsState
import com.artech.requestsappandroid.ui.screens.task_details.models.TaskDetailsViewEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel(), EventHandler<TaskDetailsViewEvent> {
    var id: Int = 1
    lateinit var navController: NavController

    private val _state = MutableStateFlow(TaskDetailsState())
    val state: StateFlow<TaskDetailsState> = _state

    override fun obtainEvent(event: TaskDetailsViewEvent) {
        when (event) {
            TaskDetailsViewEvent.EnterScreen -> loadData()
            TaskDetailsViewEvent.CompleteTask -> completeTask()
        }
    }

    private fun completeTask() {
        viewModelScope.launch {
            val response = repository.completeTask(id)
            if (response.isSuccessful) {
                navController.popBackStack()
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val response = repository.getTask(id)
            if (response.isSuccessful) {
                _state.value = _state.value.copy(
                    info = response.body()!!,
                    isLoading = false,
                )
            }
        }
    }

}