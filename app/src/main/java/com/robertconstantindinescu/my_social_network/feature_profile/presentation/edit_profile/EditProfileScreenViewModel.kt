package com.robertconstantindinescu.my_social_network.feature_profile.presentation.edit_profile

import android.net.Uri
import android.service.media.MediaBrowserService
import android.util.Log
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
import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.UpdateProfileData
import com.robertconstantindinescu.my_social_network.feature_profile.domain.use_case.ProfileUseCases
import com.robertconstantindinescu.my_social_network.feature_profile.presentation.profile.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * If the viewModel is killed, the stack is killed as well.
 */

@HiltViewModel
class EditProfileScreenViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {



    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _usernameState = mutableStateOf<StandardTextFieldState>(StandardTextFieldState())
    val usernameState: State<StandardTextFieldState> = _usernameState

    private val _githubTextFiledState = mutableStateOf<StandardTextFieldState>(
        StandardTextFieldState()
    )
    val githubTextFiledState: State<StandardTextFieldState> = _githubTextFiledState

    private val _instagramTextFieldState = mutableStateOf<StandardTextFieldState>(
        StandardTextFieldState()
    )
    val instagramTextFieldState: State<StandardTextFieldState> = _instagramTextFieldState


    private val _linkedInTextFieldState = mutableStateOf<StandardTextFieldState>(
        StandardTextFieldState()
    )
    val linkedInTextFieldState: State<StandardTextFieldState> = _linkedInTextFieldState

    private val _bioState = mutableStateOf<StandardTextFieldState>(StandardTextFieldState())
    val bioState: State<StandardTextFieldState> = _bioState

    private val _skills = mutableStateOf<SkillsState>(SkillsState())
    val skills: State<SkillsState> = _skills


    private val _profileState = mutableStateOf<ProfileState>(ProfileState())
    val profileState: State<ProfileState> = _profileState

    private val _bannerUri = mutableStateOf<Uri?>(null)
    val bannerUri: State<Uri?> = _bannerUri

   private val _profilePictureUri = mutableStateOf<Uri?>(null)
   val profilePictureUri: State<Uri?> = _profilePictureUri


    init {
        savedStateHandle.get<String>("userId")?.let { userId ->
            getSkills()
            getProfile(userId)
        }
    }




    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.EnteredUserName -> {
                _usernameState.value = usernameState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvent.EnteredGitHubUrl -> {
                _githubTextFiledState.value = githubTextFiledState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvent.EnteredInstagramUrl -> {
                _instagramTextFieldState.value = instagramTextFieldState.value.copy(
                    text = event.value
                )

            }
            is EditProfileEvent.EnteredLinkedIntUrl -> {
                _linkedInTextFieldState.value = linkedInTextFieldState.value.copy(
                    text = event.value
                )
            }
            is EditProfileEvent.CropProfileImage -> {
                _profilePictureUri.value = event.uri
            }
            is EditProfileEvent.CropBannerImage -> {
                _bannerUri.value = event.uri
            }
            is EditProfileEvent.SetSkillSelected -> {

                val result = profileUseCases.setSkillSelectedUseCase(
                    selectedSkills = skills.value.selectedSkills,
                    skillToToggle = event.skill
                )
                viewModelScope.launch {
                    when(result){
                        is Resource.Success -> {
                            _skills.value = skills.value.copy(
                                selectedSkills = result.data ?: kotlin.run {
                                    _eventFlow.emit(UiEvent.ShowSnackBar(UiText.unknownError()))
                                    return@launch
                                }
                            )
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(UiEvent.ShowSnackBar(
                                uiText = result.uiText ?: UiText.unknownError()
                            ))
                        }
                    }
                }


            }
            EditProfileEvent.UpdateProfile -> {
                //when click on the check mark to update
                updateProfile()

            }
        }
    }

    private fun updateProfile() {
        //get the data from the states for data and images
        viewModelScope.launch {
            val result = profileUseCases.updateProfileUseCase(
                updateProfileData = UpdateProfileData(
                    userName = usernameState.value.text,
                    bio = bioState.value.text,
                    githubUrl = githubTextFiledState.value.text,
                    instagramUrl = instagramTextFieldState.value.text,
                    linkedInUrl = linkedInTextFieldState.value.text,
                    skills = skills.value.selectedSkills
                ),
                profilePictureUri = profilePictureUri.value,
                bannerUri = bannerUri.value

            )

            when(result){
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.ShowSnackBar(
                        UiText.StringResource(R.string.update_profile_success)
                    ))
                    _eventFlow.emit(UiEvent.NavigateUp)
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackBar(result.uiText ?: UiText.unknownError()))

                }
            }
        }
    }


    private fun getSkills() {
        viewModelScope.launch {
            val result = profileUseCases.getSkillsUseCase()
            when (result) {
                is Resource.Success -> {
                    Log.d("ALL_SKILLS: ", result.data.toString())
                    _skills.value = skills.value.copy(
                        skills = result.data ?: kotlin.run {
                            _eventFlow.emit(
                                UiEvent.ShowSnackBar(
                                    UiText.StringResource(
                                        R.string.error_couldnt_load_skills
                                    )
                                )
                            )
                            return@launch
                        }
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackBar(result.uiText ?: UiText.unknownError()))
                }
            }
        }
    }

    private fun getProfile(userId: String) {
        viewModelScope.launch {
            _profileState.value = profileState.value.copy(
                isLoading = true
            )
            val result = profileUseCases.getProfile(userId)
            Log.d("USER_SKILLS: ", result.data!!.topSkills.toString() )
            when (result) {

                is Resource.Success -> {
                    //get data and fill the fields
                    val profile = result.data ?: kotlin.run {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                UiText.StringResource(R.string.error_couldnt_load_profile)
                            )
                        )
                        return@launch
                    }
                    _usernameState.value = usernameState.value.copy(
                        text = profile.username
                    )
                    _githubTextFiledState.value = githubTextFiledState.value.copy(
                        text = profile.gitHubUrl ?: ""
                    )
                    _instagramTextFieldState.value = instagramTextFieldState.value.copy(
                        text = profile.instagramUrl ?: ""
                    )
                    _linkedInTextFieldState.value = linkedInTextFieldState.value.copy(
                        text = profile.linkedInUrl ?:""
                    )
                    _bioState.value = bioState.value.copy(
                        text = profile.bio
                    )

                    /**
                     * When we get the data we want to set the selected skill to the state
                     */
                    _skills.value = skills.value.copy(
                        selectedSkills = profile.topSkills
                    )
                    _profileState.value = profileState.value.copy(
                        isLoading = false,
                        profile = profile
                    )
                }
                is Resource.Error -> {

                    _eventFlow.emit(UiEvent.ShowSnackBar(result.uiText ?: UiText.unknownError()))
                    return@launch

                }
            }
        }
    }

}