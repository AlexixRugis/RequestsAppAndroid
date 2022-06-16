package com.artech.requestsappandroid.ui.screens.task_details

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Velocity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.artech.requestsappandroid.common.EventHandler
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.models.AddPart
import com.artech.requestsappandroid.data.remote.models.AddParts
import com.artech.requestsappandroid.data.remote.models.Part
import com.artech.requestsappandroid.ui.screens.main.Screens
import com.artech.requestsappandroid.ui.screens.task_details.models.TaskDetailSubState
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
            TaskDetailsViewEvent.AddParts -> navigateToRepairPartsSubState()
            TaskDetailsViewEvent.LoadRepairParts -> loadRepairParts()
            is TaskDetailsViewEvent.SelectRepairPart -> selectPart(event.id)
            is TaskDetailsViewEvent.SelectedPartAmountChanged -> changeSelectedPartAmount(event.amount)
            TaskDetailsViewEvent.SubmitPartAdding -> submitPartAdding()
        }
    }

    private fun submitPartAdding() {
        viewModelScope.launch {
            val response = repository.addParts(id, AddParts(
                listOf(AddPart(
                    pk = _state.value.selectedPart!!.id,
                    amount = _state.value.selectedPartAmount
                ))
            ))
            if (response.isSuccessful) {
                _state.value = _state.value.copy(
                    subState = TaskDetailSubState.INFO,
                    selectedPartAmount = 0,
                    selectedPart = null
                )
                loadData()
            }
        }
    }

    private fun changeSelectedPartAmount(amount: Int) {
        var validAmount = amount
        if (amount < 1) validAmount = 1

        _state.value = _state.value.copy(
            selectedPartAmount = validAmount
        )
    }

    private fun selectPart(part_id: Int) {
        var part: Part? = null
        _state.value.repairParts.find {
            if (it.id == part_id) {
                part = it
                true
            } else {
                false
            }
        }

        _state.value = _state.value.copy(
            subState = TaskDetailSubState.ADD_PART,
            selectedPart = part!!
        )
    }

    private fun loadRepairParts() {
        viewModelScope.launch {
            val response = repository.getRepairParts()
            if (response.isSuccessful) {
                _state.value = _state.value.copy(repairParts = response.body()!!)
            }
        }
    }

    private fun navigateToRepairPartsSubState() {
        _state.value = _state.value.copy(subState = TaskDetailSubState.SELECT_PARTS)
        loadRepairParts()
    }

    private fun completeTask() {
        viewModelScope.launch {
            val response = repository.completeTask(id)
            if (response.isSuccessful) {
                navController.backQueue.removeLastOrNull()
                navController.navigate(Screens.Account.route)
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