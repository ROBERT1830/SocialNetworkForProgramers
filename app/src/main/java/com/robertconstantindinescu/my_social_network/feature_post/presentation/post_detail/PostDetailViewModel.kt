package com.robertconstantindinescu.my_social_network.feature_post.presentation.post_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.domain.states.StandardTextFieldState
import com.robertconstantindinescu.my_social_network.core.presentation.util.UiEvent
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.UiText
import com.robertconstantindinescu.my_social_network.feature_post.domain.use_case.PostUseCases
import com.robertconstantindinescu.my_social_network.feature_post.presentation.util.CommentError
import com.robertconstantindinescu.my_social_network.feature_post.presentation.util.PostDescriptionError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Viewmodel call use case and changes the state that the ui observes
 */
@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    //get postId using savestate handle form nav arguments
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf<PostDetailState>(PostDetailState())
    val state: State<PostDetailState> = _state

    private val _commentTextFieldState = mutableStateOf<StandardTextFieldState>(
        StandardTextFieldState(error = CommentError.FieldEmpty)
    )
    val commentTextFieldState: State<StandardTextFieldState> = _commentTextFieldState

    private val _commentState = mutableStateOf<CommentState>(CommentState())
    val commentState: State<CommentState> = _commentState


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("postId")?.let { postId ->
            loadPostDetails(postId)
            loadCommentForPost(postId)
        }
    }

    fun onEvent(event: PostDetailEvent){
        when(event){
            is PostDetailEvent.LikePost -> {

            }
            is PostDetailEvent.Comment -> {
                createComment(
                    postId = savedStateHandle.get<String>("postId") ?: "",
                    comment = commentTextFieldState.value.text
                )

            }
            is PostDetailEvent.LikeComment -> {

            }
            is PostDetailEvent.SharedPost -> {

            }
            is PostDetailEvent.EnteredComment -> {
                _commentTextFieldState.value = commentTextFieldState.value.copy(
                    text = event.comment,
                    error = if (event.comment.isBlank()) CommentError.FieldEmpty else null
                )
            }
        }
    }

    private fun createComment(postId:String, comment:String){
        viewModelScope.launch {
            _commentState.value = commentState.value.copy(
                isLoading = true
            )
            val result = postUseCases.createCommentUseCase(
                postId = postId,
                comment = comment
            )
            when(result){
                is Resource.Success ->{
                    _eventFlow.emit(UiEvent.ShowSnackBar(
                        uiText = UiText.StringResource(R.string.comment_posted)
                    ))
                    _commentState.value = commentState.value.copy(
                        isLoading = false
                    )
                    _commentTextFieldState.value = commentTextFieldState.value.copy(
                        text = "",
                        error = CommentError.FieldEmpty //reinitialize
                    )
                    loadCommentForPost(postId)
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackBar(
                        uiText = result.uiText ?: UiText.unknownError()
                    ))
                    _commentState.value = commentState.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }


    private fun loadPostDetails(postId: String){
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoadingPost = true
            )

            when(val result = postUseCases.getPostDetailsUseCase(postId)){
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        post = result.data,
                        isLoadingPost = false
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoadingPost = false
                    )
                    _eventFlow.emit(UiEvent.ShowSnackBar(result.uiText ?: UiText.unknownError()))
                }
            }
        }
    }

    private fun loadCommentForPost(postId: String){
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoadingComments = true
            )
            val result = postUseCases.getCommentsForPostUseCase(postId)
            when(result){
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        comments = result.data ?: emptyList(),
                        isLoadingComments = false
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoadingComments = false
                    )
                }
            }
        }
    }


}


































