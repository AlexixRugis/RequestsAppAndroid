package com.artech.requestsappandroid.presentation.ui.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artech.requestsappandroid.common.DataLoadingState
import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.data.remote.dto.Employee
import com.artech.requestsappandroid.data.remote.dto.RepairTasks
import com.artech.requestsappandroid.domain.use_case.get_account_data.GetAccountDataUseCase
import com.artech.requestsappandroid.domain.use_case.get_tasks.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getAccountDataUseCase: GetAccountDataUseCase,
    private val getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    private val _accountState = MutableStateFlow(DataLoadingState<Employee>())
    val accountState : StateFlow<DataLoadingState<Employee>> = _accountState

    private val _tasksState = MutableStateFlow(DataLoadingState<RepairTasks>())
    val tasksState: StateFlow<DataLoadingState<RepairTasks>> = _tasksState

    fun enterScreen() {
        getAccountData()
        getTasks()
    }

    private fun getAccountData() {
        viewModelScope.launch {
            getAccountDataUseCase.invoke().collect {
                when (it) {
                    is Resource.Loading -> _accountState.value = DataLoadingState(isLoading = true)
                    is Resource.Success -> _accountState.value = DataLoadingState(data = it.data)
                    is Resource.Error -> _accountState.value = DataLoadingState(error = it.message!!)
                }
            }
        }
    }

    private fun getTasks() {
        viewModelScope.launch {
            getTasksUseCase.invoke().collect {
                when (it) {
                    is Resource.Loading -> _tasksState.value = DataLoadingState(isLoading = true)
                    is Resource.Success -> _tasksState.value = DataLoadingState(data = it.data)
                    is Resource.Error -> _tasksState.value = DataLoadingState(error = it.message!!)
                }
            }
        }
    }
}