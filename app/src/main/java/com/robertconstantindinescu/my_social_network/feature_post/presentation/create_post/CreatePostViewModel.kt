package com.robertconstantindinescu.my_social_network.feature_post.presentation.create_post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.robertconstantindinescu.my_social_network.core.domain.states.StandardTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(

): ViewModel() {
    private val _descriptionSate = mutableStateOf<StandardTextFieldState>(StandardTextFieldState())
    val descriptionSate: State<StandardTextFieldState> = _descriptionSate

    fun setDescriptionState(state: StandardTextFieldState){
        _descriptionSate.value = state
    }
}