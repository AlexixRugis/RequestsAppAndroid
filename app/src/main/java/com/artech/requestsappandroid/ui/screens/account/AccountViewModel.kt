package com.artech.requestsappandroid.ui.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.artech.requestsappandroid.common.EventHandler
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.ui.screens.account.models.AccountViewEvent
import com.artech.requestsappandroid.ui.screens.account.models.AccountViewState
import com.artech.requestsappandroid.ui.screens.account.models.RepairTasksViewState
import com.artech.requestsappandroid.ui.screens.main.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel(), EventHandler<AccountViewEvent> {
    lateinit var navController: NavController

    private val _accountInfo = MutableStateFlow(AccountViewState())
    val accountInfo: StateFlow<AccountViewState> = _accountInfo

    private val _tasksInfo = MutableStateFlow(RepairTasksViewState())
    val tasksInfo: StateFlow<RepairTasksViewState> = _tasksInfo

    override fun obtainEvent(event: AccountViewEvent) {
        when (event) {
            AccountViewEvent.EnterScreen -> loadData()
            AccountViewEvent.RefreshTasksData -> refreshTasks()
            is AccountViewEvent.RepairRequestClicked -> handleTaskClick(event.id)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _accountInfo.value = _accountInfo.value.copy(isLoading = true)
            val response = repository.getAccount()
            if (response.isSuccessful) {
                _accountInfo.value = _accountInfo.value.copy(
                    info = response.body()!!,
                    isLoading = false
                )
            }
        }

        refreshTasks()
    }

    private fun refreshTasks() {
        viewModelScope.launch {
            _tasksInfo.value = _tasksInfo.value.copy(isLoading = true)
            val response = repository.getTasks()
            if (response.isSuccessful) {
                _tasksInfo.value = _tasksInfo.value.copy(
                    tasks = response.body()!!,
                    isLoading = false
                )
            }
        }
    }

    private fun handleTaskClick(id: Int) {
        navController.navigate(Screens.TaskDetails.route + "/${id}")
    }

    suspend fun logout(): Boolean {
        if (repository.logout().isSuccessful) {
            return true
        }
        return false
    }
}