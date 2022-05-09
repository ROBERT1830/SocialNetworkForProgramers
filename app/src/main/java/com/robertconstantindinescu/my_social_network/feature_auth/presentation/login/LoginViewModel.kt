package com.robertconstantindinescu.my_social_network.feature_auth.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.my_social_network.core.domain.states.StandardTextFieldState
import com.robertconstantindinescu.my_social_network.core.presentation.util.UiEvent
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.Screen
import com.robertconstantindinescu.my_social_network.core.util.UiText
import com.robertconstantindinescu.my_social_network.feature_auth.domain.use_case.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {

    /**
     * We create the states separately because if you wrap all of them in one
     * class, then the recomposition will trigger for all fields when you write for example in
     * password. So is not optimal in terms of ui.
     */
    private val _emailState = mutableStateOf<StandardTextFieldState>(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> = _emailState

    private val _passwordState = mutableStateOf<StandardTextFieldState>(StandardTextFieldState())
    val passwordState: State<StandardTextFieldState> = _passwordState

    private val _loginState = mutableStateOf<LoginState>(LoginState())
    val loginState: State<LoginState> = _loginState


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent){
        when(event){
            is LoginEvent.EnteredEmail -> {
                _emailState.value = emailState.value.copy(
                    text = event.email
                )
            }
            is LoginEvent.EnteredPassword -> {
                _passwordState.value = passwordState.value.copy(
                    text = event.password
                )
            }
            is LoginEvent.TogglePasswordVisibility -> {
                _loginState.value = loginState.value.copy(
                    isPasswordVisible = !loginState.value.isPasswordVisible
                )
            }

            is LoginEvent.Login -> {
                viewModelScope.launch {
                    _loginState.value = loginState.value.copy(
                        isLoading = true
                    )

                    val loginResult = loginUseCase(
                        email = emailState.value.text,
                        password = passwordState.value.text
                    )
                    _loginState.value = loginState.value.copy( isLoading = false)
                    if (loginResult.emailError != null){
                        _emailState.value = emailState.value.copy(
                            error = loginResult.emailError
                        )
                    }
                    if (loginResult.passwordError != null){
                        _passwordState.value = passwordState.value.copy(
                            error = loginResult.passwordError
                        )
                    }

                    when(loginResult.result){
                        is Resource.Success -> {
                            //fire of an event of navigation i guess.
                            _eventFlow.emit(
                                UiEvent.Navigate(Screen.MainFeedScreen.route)
                            )

                        }
                        is Resource.Error -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackBar(
                                    uiText = loginResult.result.uiText ?: UiText.unknownError()
                                )
                            )
                        }

                        else -> {}
                    }

                }

            }
        }
    }

}