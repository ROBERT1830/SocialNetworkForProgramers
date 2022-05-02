package com.robertconstantindinescu.my_social_network.feature_auth.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.domain.states.PasswordTextFieldState
import com.robertconstantindinescu.my_social_network.core.domain.states.StandardTextFieldState
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.UiText
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

    //state is persistent , shared is not.
    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> = _emailState

    private val _usernameState = mutableStateOf(StandardTextFieldState())
    val usernameState: State<StandardTextFieldState> = _usernameState

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> = _passwordState

    //state that disables the button when currently registring

    private val _registerState = mutableStateOf<RegisterState>(RegisterState())
    val registerState: State<RegisterState> = _registerState

    //can have multiple suscribers. Channel only can have one susbscriber
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
                register()
            }
            is RegisterEvent.TogglePasswordVisibility -> {
                _passwordState.value = _passwordState.value.copy(
                    isPasswordVisible = !passwordState.value.isPasswordVisible
                )
            }
        }
    }

    private fun register(){
        //not needed because we check it down there
//        if (this.emailState.value.error != null
//            && usernameState.value.error != null
//            && passwordState.value.error != null){
//            return
//        }
        viewModelScope.launch {
            _registerState.value = RegisterState(
                isLoading = true
            )

            _usernameState.value = usernameState.value.copy(error = null)
            _emailState.value = emailState.value.copy(error = null)
            _passwordState.value = passwordState.value.copy(error = null)

            val registerResult = registerUseCase(
                email = emailState.value.text,
                username = usernameState.value.text,
                password = passwordState.value.text
            )

            if (registerResult.emailError != null){
                _emailState.value = emailState.value.copy(
                    error = registerResult.emailError
                )
            }
            if (registerResult.usernameError != null){
                _usernameState.value = usernameState.value.copy(
                    error = registerResult.usernameError
                )
            }
            if (registerResult.passwordError != null){
                _passwordState.value = passwordState.value.copy(
                    error = registerResult.passwordError
                )
            }


            when(registerResult.result){
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
                    //clear the text and erorr by using an other instance of the StandardTextFieldState
                    _usernameState.value = StandardTextFieldState()
                    _emailState.value = StandardTextFieldState()
                    _passwordState.value = PasswordTextFieldState()
                }
                is Resource.Error -> {
                    _registerState.value = RegisterState(
                        successful = false,
                        message = registerResult.result.uiText,
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UiEvent.SnackBarEvent(registerResult.result.uiText ?: UiText.unknownError())
                    )
                }
                //if happened an erro the result is null
                null -> _registerState.value = RegisterState(isLoading = false)
            }
        }
    }

    sealed class UiEvent {
        data class SnackBarEvent(val uiText: UiText):UiEvent()
    }
}
























