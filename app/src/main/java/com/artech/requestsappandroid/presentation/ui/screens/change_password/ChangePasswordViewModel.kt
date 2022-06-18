package com.artech.requestsappandroid.presentation.ui.screens.change_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artech.requestsappandroid.common.ActionStatus
import com.artech.requestsappandroid.domain.use_case.change_password.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ChangePasswordViewState())
    val state: StateFlow<ChangePasswordViewState> = _state

    fun setPassword(value: String) {
        _state.value = _state.value.copy(password = value)
        validate()
    }

    fun setPasswordConfirm(value: String) {
        _state.value = _state.value.copy(passwordConfirm = value)
        validate()
    }

    fun submit() {
        if (validate()) {
            viewModelScope.launch {
                changePasswordUseCase.invoke(_state.value.password).collect {
                    if (it == ActionStatus.Success) {
                        _state.value = _state.value.copy(isChanged = true)
                    } else if (it == ActionStatus.Failure) {
                        _state.value = _state.value.copy(passwordError = "Не удалось сменить пароль")
                    }
                }
            }
        }
    }

    private fun validate() : Boolean {
        if (_state.value.password.isEmpty() || _state.value.passwordConfirm.isEmpty()) {
            _state.value = _state.value.copy(passwordError = "Заполните все поля. Это обязательно")
            return false
        }

        if (_state.value.password.length < 5) {
            _state.value = _state.value.copy(passwordError = "Длина пароля должна быть как минимум 5 символов")
            return false
        }

        if (!_state.value.password.equals(_state.value.passwordConfirm)) {
            _state.value = _state.value.copy(passwordError = "Пароли не совпадают")
            return false
        }

        _state.value = _state.value.copy(passwordError = "")

        return true
    }
}