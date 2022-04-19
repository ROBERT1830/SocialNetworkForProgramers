package com.robertconstantindinescu.my_social_network.presentation.create_post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.robertconstantindinescu.my_social_network.presentation.util.states.StandardTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.internal.platform.android.StandardAndroidSocketAdapter
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