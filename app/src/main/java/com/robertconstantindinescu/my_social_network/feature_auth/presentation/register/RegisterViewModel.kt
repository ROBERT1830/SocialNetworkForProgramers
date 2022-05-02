package com.robertconstantindinescu.my_social_network.feature_auth.presentation.register

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.domain.states.PasswordTextFieldState
import com.robertconstantindinescu.my_social_network.core.domain.states.StandardTextFieldState
import com.robertconstantindinescu.my_social_network.core.util.Constants.MIN_PASSWORD_LENGTH
import com.robertconstantindinescu.my_social_network.core.util.Constants.MIN_USERNAME_LENGTH
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.UiText
import com.robertconstantindinescu.my_social_network.feature_auth.domain.models.AuthError
import com.robertconstantindinescu.my_social_network.feature_auth.domain.use_case.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//gets data from use cases and maps it to state useful for ui.
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    /**
     * We decided to do a state for each and every field because when you change a
     * state lets say in username. because all the fields are in one wrapper class
     * called state the recomposition will trigger again for eac field. So
     * is quite often the recomposition for each field.
     *
     * So havin a state for each input is a better performance ui.
     */

    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> = _emailState

    private val _usernameState = mutableStateOf(StandardTextFieldState())
    val usernameState: State<StandardTextFieldState> = _usernameState

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> = _passwordState

    //state that disables the button when currently registring

    private val _registerState = mutableStateOf<RegisterState>(RegisterState())
    val registerState: State<RegisterState> = _registerState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: RegisterEvent){
        when(event){
            is RegisterEvent.EnteredUsername -> {
                _usernameState.value = _usernameState.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.EnteredEmail -> {
                _emailState.value = _emailState.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.EnteredPassword -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.Register -> {
                validateUsername(
                    usernameState.value.text
                )
                validateEmail(emailState.value.text)
                validatePassword(passwordState.value.text)
                registerIfNoErrors()
            }
            is RegisterEvent.TogglePasswordVisibility -> {
                _passwordState.value = _passwordState.value.copy(
                    isPasswordVisible = !passwordState.value.isPasswordVisible
                )
            }
        }
    }

    private fun registerIfNoErrors(){
        if (this.emailState.value.error != null
            && usernameState.value.error != null
            && passwordState.value.error != null){
            return
        }
        viewModelScope.launch {
            _registerState.value = RegisterState(
                isLoading = true
            )
            val result = registerUseCase(
                email = emailState.value.text,
                username = usernameState.value.text,
                password = passwordState.value.text
            )
            when(result){
                is Resource.Success -> {
                    //create another register state
                    _registerState.value = RegisterState(
                        successful = true,
                        message = null,
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UiEvent.SnackBarEvent(UiText.StringResource( R.string.success_registration))
                    )
                }
                is Resource.Error -> {
                    _registerState.value = RegisterState(
                        successful = false,
                        message = result.uiText,
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UiEvent.SnackBarEvent(result.uiText ?: UiText.unknownError())
                    )
                }
            }
        }
    }

    private fun validateUsername(username: String){
        val trimmedUsername = username.trim()
        if (trimmedUsername.isBlank()){
            _usernameState.value = _usernameState.value.copy(
                error = AuthError.FieldEmpty
            )
            return
        }
        if (trimmedUsername.length < MIN_USERNAME_LENGTH){
            _usernameState.value = _usernameState.value.copy(
                error = AuthError.InputTooShor
            )
            return
        }
        //if filter passes then reset the error to null for not showing in the ui
        _usernameState.value = _usernameState.value.copy(
            error = null
        )
    }
    private fun validateEmail(email: String){
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank()){
            _emailState.value = _emailState.value.copy(
                error = AuthError.FieldEmpty
            )
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _emailState.value = _emailState.value.copy(
                error = AuthError.InvalidEmail
            )
            return
        }
        _emailState.value = _emailState.value.copy(
            error = null
        )
    }

    private fun validatePassword(password: String){
        if (password.isBlank()){
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.FieldEmpty
            )
            return
        }
        if (password.length < MIN_PASSWORD_LENGTH){
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.InputTooShor
            )
            return
        }

        //any --> returns true if a character matches the condition
        val capitalLettersInPassword = password.any{
             it.isUpperCase()
        }
        val numberInPassword = password.any{
            it.isDigit()
        }

        if (!capitalLettersInPassword || !numberInPassword){
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.InvalidPassword
            )
            return
        }
        _passwordState.value = _passwordState.value.copy(
            error = null
        )
    }


    sealed class UiEvent {
        data class SnackBarEvent(val uiText: UiText):UiEvent()
    }
}
























