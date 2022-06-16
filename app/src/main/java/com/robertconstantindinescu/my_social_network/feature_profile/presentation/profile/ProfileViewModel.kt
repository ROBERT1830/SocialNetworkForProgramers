package com.robertconstantindinescu.my_social_network.feature_profile.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import com.robertconstantindinescu.my_social_network.core.domain.use_case.GetOwnUserIdUseCase
import com.robertconstantindinescu.my_social_network.core.presentation.PagingState
import com.robertconstantindinescu.my_social_network.core.presentation.util.UiEvent
import com.robertconstantindinescu.my_social_network.core.util.*
import com.robertconstantindinescu.my_social_network.feature_post.domain.use_case.PostUseCases
import com.robertconstantindinescu.my_social_network.feature_post.presentation.person_list.PostEvent
import com.robertconstantindinescu.my_social_network.feature_profile.domain.use_case.GetProfileUseCase
import com.robertconstantindinescu.my_social_network.feature_profile.domain.use_case.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val postUseCases: PostUseCases,
    private val getOwnUserId: GetOwnUserIdUseCase,
    private val savedStateHandle: SavedStateHandle, //to get the id directly. Comes from navigation arguments. Is basically a bundle tha tcontains navigation arguments.
    private val postLiker: PostLiker
) : ViewModel() {

    private val _toolbarState = mutableStateOf<ProfileToolbarState>(ProfileToolbarState())
    val toolbarState: State<ProfileToolbarState> = _toolbarState


    private val _state = mutableStateOf<ProfileState>(ProfileState())
    val state: State<ProfileState> = _state

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()


    //Observe the from directly from the ui.
//    val posts = profileUseCases.getPostsForProfileUseCase(
//        savedStateHandle.get<String>("userId") ?: getOwnUserId()
//    ).cachedIn(viewModelScope)

    private var page = 0

    //the state of the paging that contains the post
    private val _pagingState = mutableStateOf<PagingState<Post>>(PagingState())
    val pagingState: State<PagingState<Post>> = _pagingState

    private val paginator = DefaultPaginator<Post>(
        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(
                isLoading = isLoading
            )
        },
        onRequest = { page ->
            val userId = savedStateHandle.get<String>("userId") ?: getOwnUserId()
            profileUseCases.getPostsForProfileUseCase(
                userId = userId,
                page = page
            )
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

    fun setExpandedRatio(ratio: Float) {
        _toolbarState.value = _toolbarState.value.copy(expandedRatio = ratio)
        println("UPDATING TOOLBAR STATE TO $toolbarState")
    }

    fun setToolbarOffsetY(value: Float) {
        _toolbarState.value = _toolbarState.value.copy(toolbarOffsetY = value)
        println("UPDATING TOOLBAR STATE TO $toolbarState")
    }

    init {
        loadNextPosts()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.GetProfile -> {

            }
            is ProfileEvent.Logout -> {
                profileUseCases.logout()
            }
            is ProfileEvent.ShowLogoutDialog -> {
                _state.value = state.value.copy(
                    isLogoutDialogVisible = true
                )
            }
            is ProfileEvent.DismissLogoutDialog -> {
                _state.value = state.value.copy(
                    isLogoutDialogVisible = false
                )
            }
            is ProfileEvent.LikedPost -> {
                viewModelScope.launch {
                    toggleLikeForParent(
                        parentId = event.postId,

                    )
                }

            }
        }
    }

    fun getProfile(userId: String?) {

        //Load profile info
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            //if user id is null, then pick our own to see our profile
            val result = profileUseCases.getProfile(userId ?: getOwnUserId()) //get own user id if we dont passs a userId
            when (result) {
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
                    _eventFlow.emit(
                        UiEvent.ShowSnackBar(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }


    }

    fun loadNextPosts() {
        viewModelScope.launch {

            paginator.loadNextItems()

//            //paginator.loadNextItems()
//            _pagingState.value = pagingState.value.copy(
//                isLoading = true
//            )
//            val userId = savedStateHandle.get<String>("userId") ?: ""
//            //val result = profileUseCases.getPostsForProfileUseCase(userId = userId, page= page)
//            when (result) {
//                is Resource.Success -> {
//                    val posts = result.data ?: emptyList()
////                    _pagingState.value = pagingState.value.copy(
////                        items = pagingState.value.items + posts,
////                        //if the post is empty that means that we reach the end because the api just return the empty list. so we are already loaded all the posts.
////                        endReached = posts.isEmpty(),
////                        isLoading = false
////
////                        //we reach the end if the list that we get from the api is empty
////                    )
//                    //increase the page if not we are calling allways the same page
//                    page++
//
//                    Timber.d("Pagin state changed to ${pagingState.value}")
//
//                }
//                is Resource.Error -> {
////                    _eventFlow.emit(UiEvent.ShowSnackBar(result.uiText ?: UiText.unknownError()))
////                    _pagingState.value = pagingState.value.copy(
//                        isLoading = false
//                    )
//                }
//            }
        }
    }

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
                        items = posts,
                    )
                }
            )
            // SAVE SO MANY CODE
//            //map through the list of our posts and actually change  the like state for the post we liked
//            //find the current post
//            val post = pagingState.value.items.find {
//                it.id == parentId
//            }
//            //check if the post is currently liked
//            val currentlyLiked = post?.isLiked == true
//            val currentLikeCount = post?.likeCount ?: 0
//            //put this here before the user case because we want to toggle the like quickly
//            val newPosts  = pagingState.value.items.map { post ->
//                if (post.id == parentId){
//                    post.copy(
//                        isLiked = !post.isLiked,
//                        likeCount = if (currentlyLiked){
//                            post.likeCount -1
//                        }else post.likeCount + 1
//                    )
//                }else post
//            }
//            //toggle the like
//            val result = postUseCases.toggleLikeForParentUseCase(
//                parentId = parentId,
//                parentType = ParentType.Post.type,
//                isLiked = currentlyLiked
//            )
//
//            //swtich the like
//            _pagingState.value = pagingState.value.copy(
//                items = newPosts
//            )
//            //what we want to achieve is to togle the like before we make the request to the server
//            //if the server turns back a error response then we want to switch the like back.
//            when (result) {
//                is Resource.Success -> {
//                   Unit// _eventFlow.emit(PostEvent.OnLiked)
//                }
//                is Resource.Error -> {
//                    //reset the toggle. because then we got a error in the api and the like was not aknowdledge
//                    val oldPosts  = pagingState.value.items.map { post ->
//                        if (post.id == parentId){
//                            post.copy(isLiked = currentlyLiked,
//                                likeCount = currentLikeCount
//                            )
//                        }else post
//                    }
//                    _pagingState.value = pagingState.value.copy(
//                        items = newPosts
//                    )
//                }
//            }
        }
    }

}



















