package com.robertconstantindinescu.my_social_network.feature_post.presentation.main_feed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import com.robertconstantindinescu.my_social_network.core.presentation.PagingState
import com.robertconstantindinescu.my_social_network.core.presentation.util.UiEvent
import com.robertconstantindinescu.my_social_network.core.util.*
import com.robertconstantindinescu.my_social_network.feature_post.domain.use_case.PostUseCases
import com.robertconstantindinescu.my_social_network.feature_post.presentation.person_list.PostEvent
import com.robertconstantindinescu.my_social_network.feature_post.presentation.post_detail.PostDetailEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFeedViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val postLiker: PostLiker
): ViewModel() {

//    private val _state = mutableStateOf<MainFeedState>(MainFeedState())
//    val state: State<MainFeedState> = _state

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _pagingState = mutableStateOf<PagingState<Post>>(PagingState())
    val pagingState: State<PagingState<Post>> = _pagingState
    //This should not go inside the viewmodel becase we need the api and here in the
    //viewmodel we dont provide it. It must be in the viewmodel.
    ///val posts = postUseCases.getPostForFollowsUseCase().cachedIn(viewModelScope) //we already have the flow in the user case so here is not needed


    private val paginator = DefaultPaginator(
        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(
                isLoading = isLoading
            )
        },
        onRequest = { page ->
            postUseCases.getPostForFollowsUseCase(page = page)
        },
        onSuccess = { posts ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + posts,
                endReached = posts.isEmpty(),
                isLoading = false
            )
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.ShowSnackBar(uiText))
        }
    )
    init {
        loadNextPosts()
    }
    fun onEvent(event: MainFeedEvent){

        when(event) {
            is MainFeedEvent.LikedPost -> {
                toggleLikeForParent(event.postId)
            }
//            is MainFeedEvent.DeletePost -> {
//                deletePost(event.post.id)
//            }
        }
       // when(event){
//            is MainFeedEvent.LoadMorePosts -> {
//                _state.value = state.value.copy(
//                    isLoadingNewPosts = true
//                )
//            }
//            is MainFeedEvent.LoadedPage -> {
//                //reset the progress bar
//                _state.value = state.value.copy(
//                    isLoadingFirstTime = false,
//                    isLoadingNewPosts = false
//                )
//            }
//
//            is MainFeedEvent.LikedPost -> {
//
//            }
        //}
    }

    fun loadNextPosts() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    //w
    private fun toggleLikeForParent(
        parentId: String,
    ) {
        viewModelScope.launch {
            postLiker.toggleLike(
                posts = pagingState.value.items,
                parentId = parentId,
                onRequest = { isLiked ->
                    postUseCases.toggleLikeForParentUseCase(
                        parentId = parentId,
                        parentType = ParentType.Post.type,
                        isLiked = isLiked
                    )
                },
                onStateUpdated = { posts ->
                    _pagingState.value = pagingState.value.copy(
                        items = posts
                    )
                }
            )
        }
    }



}