package com.artech.requestsappandroid.presentation.ui.screens.login

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artech.requestsappandroid.common.EventHandler
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.dto.AuthenticationData
import com.artech.requestsappandroid.presentation.ui.screens.login.models.LoginEvent
import com.artech.requestsappandroid.presentation.ui.screens.login.models.LoginViewState
import com.artech.requestsappandroid.presentation.ui.screens.main.MainViewModel
import com.artech.requestsappandroid.presentation.ui.screens.main.models.MainViewEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel(), EventHandler<LoginEvent> {
    lateinit var mainViewModel: MainViewModel
    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

    private val _loginState = MutableStateFlow(LoginViewState())
    val loginState: StateFlow<LoginViewState> = _loginState

    override fun obtainEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.Login -> login()
            LoginEvent.ChangePasswordVisibility -> changePasswordVisibility()
            is LoginEvent.EmailChanged -> changeEmail(event.value)
            is LoginEvent.PasswordChanged -> changePassword(event.value)

        }
    }

    private fun login() {
        when {
            _loginState.value.email.isEmpty() -> {
                _loginState.value = _loginState.value.copy(emailErrorState = true)
            }
            _loginState.value.password.isEmpty() -> {
                _loginState.value = _loginState.value.copy(passwordErrorState = true)
            }
            else -> {
                _loginState.value = _loginState.value.copy(
                    emailErrorState = false,
                    passwordErrorState = false
                )

                viewModelScope.launch {
                    _loginState.value = _loginState.value.copy(isFormActive = false)

                    val response = repository.login(
                        AuthenticationData(
                            _loginState.value.email,
                            _loginState.value.password
                        )
                    )

                    if (response.isSuccessful) {
                        mainViewModel.obtainEvent(MainViewEvent.LoginApplication)
                    } else {
                        Toast.makeText(
                            context,
                            "Ошибка при входе",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    _loginState.value = _loginState.value.copy(isFormActive = true)
                }
            }
        }
    }

    private fun changePasswordVisibility() {
        _loginState.value = _loginState.value.copy(isPasswordHidden = !_loginState.value.isPasswordHidden)
    }

    private fun changeEmail(value: String) {
        _loginState.value = _loginState.value.copy(email = value, emailErrorState = false)
    }

    private fun changePassword(value: String) {
        _loginState.value = _loginState.value.copy(password = value, passwordErrorState = false)
    }
}