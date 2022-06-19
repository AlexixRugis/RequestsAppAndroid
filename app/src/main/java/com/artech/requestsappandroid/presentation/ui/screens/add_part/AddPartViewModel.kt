package com.artech.requestsappandroid.presentation.ui.screens.add_part

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artech.requestsappandroid.common.ActionStatus
import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.domain.use_case.add_part.AddPartUseCase
import com.artech.requestsappandroid.domain.use_case.get_repair_part.GetRepairPartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPartViewModel @Inject constructor(
    private val getRepairPartUseCase: GetRepairPartUseCase,
    private val addPartUseCase: AddPartUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _taskId = -1
    private var _partId = -1

    private val _state = MutableStateFlow(AddPartViewState())
    val state : StateFlow<AddPartViewState> = _state

    init {
        _taskId = savedStateHandle.get<String>("taskId")!!.toInt()
        _partId = savedStateHandle.get<String>("partId")!!.toInt()

    }

    fun initialize() {
        loadPart()
    }

    private fun loadPart() {
        viewModelScope.launch {
            getRepairPartUseCase.invoke(_partId).collect {
                when (it) {
                    is Resource.Loading -> _state.value = AddPartViewState(isLoading = true)
                    is Resource.Success -> _state.value = AddPartViewState(repairPart = it.data)
                    is Resource.Error -> _state.value = AddPartViewState(error = it.message!!)
                }
            }
        }
    }

    fun changeAmount(newAmount : Int) {
        var validAmount = newAmount
        if (validAmount < 1) validAmount = 1

        _state.value = _state.value.copy(amount = validAmount)
    }

    fun addPart() {
        viewModelScope.launch {
            addPartUseCase.invoke(_taskId, _partId, _state.value.amount).collect {
                if (it == ActionStatus.Success) {
                    _state.value = _state.value.copy(isAdded = true)
                }
            }
        }
    }
}