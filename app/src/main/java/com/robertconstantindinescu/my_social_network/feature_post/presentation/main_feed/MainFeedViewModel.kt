package com.robertconstantindinescu.my_social_network.feature_post.presentation.main_feed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.robertconstantindinescu.my_social_network.feature_post.domain.use_case.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainFeedViewModel @Inject constructor(
    private val postUseCases: PostUseCases
): ViewModel() {

    private val _state = mutableStateOf<MainFeedState>(MainFeedState())
    val state: State<MainFeedState> = _state


    //This should not go inside the viewmodel becase we need the api and here in the
    //viewmodel we dont provide it. It must be in the viewmodel.
    val posts = postUseCases.getPostForFollowsUseCase().cachedIn(viewModelScope) //we already have the flow in the user case so here is not needed


    fun onEvent(event: MainFeedEvent){
        when(event){
            is MainFeedEvent.LoadMorePosts -> {
                _state.value = state.value.copy(
                    isLoadingNewPosts = true
                )
            }
            is MainFeedEvent.LoadedPage -> {
                //reset the progress bar
                _state.value = state.value.copy(
                    isLoadingFirstTime = false,
                    isLoadingNewPosts = false
                )
            }
        }
    }
}