package com.robertconstantindinescu.my_social_network.feature_post.presentation.create_post

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.my_social_network.core.domain.states.StandardTextFieldState
import com.robertconstantindinescu.my_social_network.feature_post.domain.use_case.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(

    private val useCases: PostUseCases
): ViewModel() {
    private val _descriptionSate = mutableStateOf<StandardTextFieldState>(StandardTextFieldState())
    val descriptionSate: State<StandardTextFieldState> = _descriptionSate

    private val _chosenImageUri = mutableStateOf<Uri?>(null)
    val chosenImage: State<Uri?> = _chosenImageUri




    fun onEvent(event: CreatePostEvent){
        when(event){
            is CreatePostEvent.EnterDescription -> {
                _descriptionSate.value = descriptionSate.value.copy(
                    text = event.value
                )
            }
            is CreatePostEvent.PickImage -> {
                _chosenImageUri.value = event.uri
            }
            is CreatePostEvent.PostImage -> {
                chosenImage.value?.let { uri ->
                   viewModelScope.launch {
                      useCases.createPostUseCase(

                           description = descriptionSate.value.text,
                           imageUri = uri
                       )
                   }
                }

            }
        }
    }
}