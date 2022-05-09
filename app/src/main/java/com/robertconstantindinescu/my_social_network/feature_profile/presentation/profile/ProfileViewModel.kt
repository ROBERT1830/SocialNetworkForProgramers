package com.robertconstantindinescu.my_social_network.feature_profile.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.my_social_network.core.presentation.util.UiEvent
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.UiText
import com.robertconstantindinescu.my_social_network.feature_profile.domain.use_case.GetProfileUseCase
import com.robertconstantindinescu.my_social_network.feature_profile.domain.use_case.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    savedStateHandle: SavedStateHandle //to get the id directly. Comes from navigation arguments. Is basically a bundle tha tcontains navigation arguments.
): ViewModel(){

    private val _toolbarState = mutableStateOf<ProfileToolbarState>(ProfileToolbarState())
    val toolbarState: State<ProfileToolbarState> = _toolbarState


    init {
        savedStateHandle.get<String>("userId")?.let { userId ->
                getProfile(userId)
        }
    }

    private val _state = mutableStateOf<ProfileState>(ProfileState())
    val state: State<ProfileState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun setExpandedRatio(ratio: Float){
        _toolbarState.value = _toolbarState.value.copy(expandedRatio = ratio)
        println("UPDATING TOOLBAR STATE TO $toolbarState")
    }

    fun setToolbarOffsetY(value: Float){
        _toolbarState.value = _toolbarState.value.copy(toolbarOffsetY = value)
        println("UPDATING TOOLBAR STATE TO $toolbarState")
    }

    fun onEvent(event:ProfileEvent){
        when(event){
            is ProfileEvent.GetProfile -> {

            }
        }
    }

    private fun getProfile(userId:String){
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            val result = profileUseCases.getProfile(userId)
            when(result){
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        profile = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    //data is null
                    _state.value = state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowSnackBar(
                        uiText = result.uiText ?: UiText.unknownError()
                    ))
                }
            }
        }

    }

}



















