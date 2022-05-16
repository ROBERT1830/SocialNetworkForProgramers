package com.robertconstantindinescu.my_social_network.feature_activity.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.robertconstantindinescu.my_social_network.feature_activity.domain.use_case.GetActivitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(

    private val getActivitiesUseCase: GetActivitiesUseCase
): ViewModel() {

    val activities = getActivitiesUseCase().cachedIn(viewModelScope)

    private val _state = mutableStateOf<ActivityState>(ActivityState())
    val state: State<ActivityState> = _state



    fun onEvent(event: ActivityEvent){
        when(event){
            is ActivityEvent.ClickedOnUser -> {

            }
            is ActivityEvent.ClickedOnParent -> {

            }
        }
    }
}