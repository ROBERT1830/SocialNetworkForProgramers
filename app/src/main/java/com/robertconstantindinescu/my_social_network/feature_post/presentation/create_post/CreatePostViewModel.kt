package com.robertconstantindinescu.my_social_network.feature_post.presentation.create_post

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.domain.states.StandardTextFieldState
import com.robertconstantindinescu.my_social_network.core.presentation.util.UiEvent
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.UiText
import com.robertconstantindinescu.my_social_network.feature_post.domain.use_case.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(

    private val useCases: PostUseCases
) : ViewModel() {
    private val _descriptionSate = mutableStateOf<StandardTextFieldState>(StandardTextFieldState())
    val descriptionSate: State<StandardTextFieldState> = _descriptionSate

    private val _isLoading = mutableStateOf<Boolean>(false)
    val isLoading: State<Boolean> = _isLoading

    private val _chosenImageUri = mutableStateOf<Uri?>(null)
    val chosenImage: State<Uri?> = _chosenImageUri

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    //val destUri = Uri.fromFile(File())


    fun onEvent(event: CreatePostEvent) {
        when (event) {
            is CreatePostEvent.EnterDescription -> {
                _descriptionSate.value = descriptionSate.value.copy(
                    text = event.value
                )
            }
            is CreatePostEvent.PickImage -> {
                _chosenImageUri.value = event.uri
            }
            is CreatePostEvent.CropImage -> {
                _chosenImageUri.value = event.uri
            }
            is CreatePostEvent.PostImage -> {
                viewModelScope.launch {
                    _isLoading.value = true
                    val result = useCases.createPostUseCase(
                        description = descriptionSate.value.text,
                        imageUri = chosenImage.value
                    )

                    when (result) {
                        is Resource.Success -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackBar(
                                    uiText = UiText.StringResource(R.string.post_created)
                                )
                            )
                            _eventFlow.emit(UiEvent.NavigateUp)
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackBar(
                                    result.uiText ?: UiText.unknownError()
                                )
                            )
                        }
                    }
                    _isLoading.value = false
                }

            }
        }
    }
}
































