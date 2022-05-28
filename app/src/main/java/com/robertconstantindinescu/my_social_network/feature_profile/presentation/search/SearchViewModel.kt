package com.robertconstantindinescu.my_social_network.feature_profile.presentation.search

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.my_social_network.core.domain.states.StandardTextFieldState
import com.robertconstantindinescu.my_social_network.core.presentation.util.UiEvent
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.UiText
import com.robertconstantindinescu.my_social_network.feature_profile.domain.use_case.ProfileUseCases
import com.robertconstantindinescu.my_social_network.feature_profile.domain.use_case.ToggleFollowStateForUserUseCase
import com.robertconstantindinescu.my_social_network.feature_profile.domain.util.ProfileConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
) : ViewModel() {

    private val _searchFieldState = mutableStateOf<StandardTextFieldState>(StandardTextFieldState())
    val searchFieldState: State<StandardTextFieldState> = _searchFieldState

    private val _searchState = mutableStateOf<SearchState>(SearchState())
    val searchState: State<SearchState> = _searchState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    //this job will delay the coroutine for whatever we want to wait to start the search.
    //if we type an other character we cancel the running coroutine and we start an other one and
    //delay it again.
    private var searchJob: Job? = null

    //the job of that event is like a switch, not to search the user specifically.
    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.Query -> {
                searchUser(event.query)
            }
            is SearchEvent.ToggleFollowState -> {
                //loop over the current searched list from searchState and toggle
                //the follow state and make a follow or unfollow reqeust.
                toggleFollowStateForUser(event.userId)
            }
        }
    }

    private fun toggleFollowStateForUser(userId: String) {
        viewModelScope.launch {


            /**
             * That needs to be executed first to not change inmediatly the
             * isFollowing from status.
             */
            /*Take a look at userItems (contains all users in db that could be followed or not,
            i mean could have isFollowing true or false) and find the user  with the user id we want to toggle
            * the status and check if the status isFollowing of that objects is true. */
            val isFollowing = searchState.value.userItems.find {
                it.userId == userId
            }?.isFollowing == true
            println("isFollowing before: $isFollowing")

            //This is for toggle or untoggle the icon for follow or unfollow.
            _searchState.value = searchState.value.copy(
                userItems = searchState.value.userItems.map {
                    //if we find the user in the list
                    if (it.userId == userId){
                        it.copy(isFollowing = !it.isFollowing)
                    }else it
                }
            )

            //make the request
            val result = profileUseCases.toggleFollowStateForUserUseCase(
                userId = userId,
                isFollowing = isFollowing
            )
            println("isFollowing after: $isFollowing")

            when(result){
                is Resource.Success -> Unit
                //if there is an error toggle back the icon to previous state
                is Resource.Error -> {
                    _searchState.value = searchState.value.copy(
                        userItems = searchState.value.userItems.map {
                            //if we find the user in the list
                            if (it.userId == userId){
                                it.copy(isFollowing = isFollowing)
                            }else it
                        }
                    )
                    _eventFlow.emit(UiEvent.ShowSnackBar(
                        uiText = result.uiText ?: UiText.unknownError()
                    ))
                }
            }
        }
    }


    private fun searchUser(query: String) {
        _searchFieldState.value = searchFieldState.value.copy(
            text = query
        )
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(ProfileConstants.SEARCH_DELAY)

            _searchState.value = searchState.value.copy(
                isLoading = true
            )

            val result = profileUseCases.searchUserUseCase(query)

            when (result) {
                is Resource.Success -> {
                    _searchState.value = searchState.value.copy(
                        userItems = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _searchFieldState.value = searchFieldState.value.copy(
                        error = SearchError(
                            message = result.uiText ?: UiText.unknownError()
                        )
                    )
                    _searchState.value = searchState.value.copy(
                        isLoading = false
                    )
                }
            }
        }


    }


}




































