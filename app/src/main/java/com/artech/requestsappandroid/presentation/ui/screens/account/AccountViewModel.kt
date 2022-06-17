package com.artech.requestsappandroid.presentation.ui.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.domain.use_case.get_account_data.GetAccountDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getAccountDataUseCase: GetAccountDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AccountViewState())
    val state : StateFlow<AccountViewState> = _state

    init {
        getAccountData()
    }

    private fun getAccountData() {
        viewModelScope.launch {
            getAccountDataUseCase.invoke().collect {
                when (it) {
                    is Resource.Loading -> _state.value = AccountViewState(isLoading = true)
                    is Resource.Success -> _state.value = AccountViewState(accountData = it.data)
                    is Resource.Error -> _state.value = AccountViewState(error = it.message!!)
                }
            }
        }
    }
}