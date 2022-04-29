package com.robertconstantindinescu.my_social_network.feature_profile.presentation.edit_profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.robertconstantindinescu.my_social_network.core.domain.states.StandardTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileScreenViewModel @Inject constructor(

): ViewModel(){

    private val _usernameState = mutableStateOf<StandardTextFieldState>(StandardTextFieldState())
    val usernameState: State<StandardTextFieldState> = _usernameState

    private val _githubTextFiledState = mutableStateOf<StandardTextFieldState>(
        StandardTextFieldState()
    )
    val githubTextFiledState: State<StandardTextFieldState> = _githubTextFiledState

    private val _instagramTextFieldState = mutableStateOf<StandardTextFieldState>(
        StandardTextFieldState()
    )
    val instagramTextFieldState: State<StandardTextFieldState> = _instagramTextFieldState


    private val _linkedInTextFieldState = mutableStateOf<StandardTextFieldState>(
        StandardTextFieldState()
    )
    val linkedInTextFieldState: State<StandardTextFieldState> = _linkedInTextFieldState

    private val _bioState = mutableStateOf<StandardTextFieldState>(StandardTextFieldState())
    val bioState: State<StandardTextFieldState> = _bioState

    fun setBioState(state: StandardTextFieldState){
        _bioState.value = state
    }

    fun setLinkedInTextFieldState(state: StandardTextFieldState){
        _linkedInTextFieldState.value = state
    }

    fun setInstagramTextFieldState(state: StandardTextFieldState){
        _instagramTextFieldState.value = state
    }

    fun setUsernameState(state: StandardTextFieldState){
        _usernameState.value = state
    }

    fun setGithubTextFiledState(state: StandardTextFieldState){
        _githubTextFiledState.value = state
    }


}