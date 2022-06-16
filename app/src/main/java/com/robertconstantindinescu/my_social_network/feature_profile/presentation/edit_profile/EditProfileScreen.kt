package com.robertconstantindinescu.my_social_network.feature_profile.presentation.edit_profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.presentation.components.StandardTextField
import com.robertconstantindinescu.my_social_network.core.presentation.components.StandardToolBar
import com.robertconstantindinescu.my_social_network.feature_profile.presentation.edit_profile.components.Chip
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.ProfilePictureSizeLage
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceLarge
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceMedium
import com.robertconstantindinescu.my_social_network.core.presentation.util.CropActivityResultContract
import com.robertconstantindinescu.my_social_network.core.presentation.util.UiEvent
import com.robertconstantindinescu.my_social_network.core.presentation.util.asString
import com.robertconstantindinescu.my_social_network.feature_profile.presentation.util.EditProfileError
import kotlinx.coroutines.flow.collectLatest


@Composable
fun EditProfileScreen(
    scaffoldState: ScaffoldState,
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: EditProfileScreenViewModel = hiltViewModel(),
    profilePictureSize: Dp = ProfilePictureSizeLage

) {

    val profileState = viewModel.profileState.value
    val context = LocalContext.current

    val cropProfilePictureLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(1f, 1f),

        ) {
        viewModel.onEvent(EditProfileEvent.CropProfileImage(it))
    }

    val cropBannerImageLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(5f, 2f),

        ) {
        viewModel.onEvent(EditProfileEvent.CropBannerImage(it))
    }

    val profilePictureGalleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent() //this makes the user to pick content and will receive content://+Uri for the content.
        ) {
            if(it == null) {
                return@rememberLauncherForActivityResult
            }
            //launch the crop
            cropProfilePictureLauncher.launch(it) // this it will be the input uri for the image


        }

    val bannerImageGalleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent() //this makes the user to pick content and will receive content://+Uri for the content.
        ) {
            if(it == null) {
                return@rememberLauncherForActivityResult
            }
            cropBannerImageLauncher.launch(it) // this it will be the input uri for the image

        }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
            when (it) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.uiText.asString(context)
                    )
                }
                is UiEvent.NavigateUp -> {
                    onNavigateUp()
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StandardToolBar(
            onNavigateUp = { onNavigateUp() },
            showBackArrow = true,
            navAction = {
                IconButton(onClick = {
                    viewModel.onEvent(EditProfileEvent.UpdateProfile)
                }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.save_changes),
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            },
            title = {
                Text(
                    text = stringResource(id = R.string.edit_your_profile),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground

                )
            }
        )
        //SCROLLABLE CONTENT
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            BannerEditSection(

                bannerImage = rememberImagePainter(
                    data = viewModel.bannerUri.value ?: profileState.profile?.bannerUrl,
                    imageLoader = imageLoader
                ),
//                bannerImage = rememberAsyncImagePainter(
//                    ImageRequest.Builder(LocalContext.current)
//                        //when the uri from the cropper is null, then the image to display comes from the api
//                        // That happens at the first laucnh of the screen. When select an other one, it will be displayed the
//                        // croper.
//                        .data(data = viewModel.bannerUri.value ?: profileState.profile?.bannerUrl)
//                        .apply(block = fun ImageRequest.Builder.() {
//                            crossfade(true)
//                        }).build()
//                ),
                profileImage =  rememberImagePainter(
                    data =  viewModel.profilePictureUri.value ?: profileState.profile?.profilePictureUrl,
                    imageLoader = imageLoader
                ),
//                profileImage = rememberAsyncImagePainter(
//                    ImageRequest.Builder(LocalContext.current)
//                        .data(data = viewModel.profilePictureUri.value ?: profileState.profile?.profilePictureUrl)
//                        .apply(block = fun ImageRequest.Builder.() {
//                            crossfade(true)
//                        }).build()
//                ),
                profilePictureSize = profilePictureSize,
                onBannerClick = {
                    bannerImageGalleryLauncher.launch("image/*")
                },
                onProfilePictureClick = {
                    profilePictureGalleryLauncher.launch("image/*")
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceLarge)
            ) {
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = viewModel.usernameState.value.text,
                    hint = stringResource(id = R.string.user_name),
                    error = when (viewModel.usernameState.value.error) {
                        is EditProfileError.FieldEmpty -> {
                            stringResource(id = R.string.error_field_empty)
                        }
                        else -> ""

                    },
                    leadingIcon = Icons.Default.Person,
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredUserName(it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = viewModel.githubTextFiledState.value.text,
                    hint = stringResource(id = R.string.github_profile_url),
                    error = when (viewModel.githubTextFiledState.value.error) {
                        is EditProfileError.FieldEmpty -> {
                            stringResource(id = R.string.error_field_empty)
                        }
                        else -> ""

                    },
                    leadingIcon = ImageVector.vectorResource(id = R.drawable.ic_github_icon_1),
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredGitHubUrl(it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = viewModel.instagramTextFieldState.value.text,
                    hint = stringResource(id = R.string.instagram_profile_url),
                    error = when (viewModel.instagramTextFieldState.value.error) {
                        is EditProfileError.FieldEmpty -> {
                            stringResource(id = R.string.error_field_empty)
                        }
                        else -> ""

                    },
                    leadingIcon = ImageVector.vectorResource(id = R.drawable.ic_instagram_glyph_1),
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredInstagramUrl(it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = viewModel.linkedInTextFieldState.value.text,
                    hint = stringResource(id = R.string.linked_in_profile),
                    error = when (viewModel.linkedInTextFieldState.value.error) {
                        is EditProfileError.FieldEmpty -> {
                            stringResource(id = R.string.error_field_empty)
                        }
                        else -> ""

                    },
                    leadingIcon = ImageVector.vectorResource(id = R.drawable.ic_linkedin_icon_1),
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredBio(it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = viewModel.bioState.value.text,
                    hint = stringResource(id = R.string.your_bio),
                    error = when (viewModel.bioState.value.error) {
                        is EditProfileError.FieldEmpty -> {
                            stringResource(id = R.string.error_field_empty)
                        }
                        else -> ""

                    },
                    singleLine = false,
                    maxLines = 3,
                    leadingIcon = Icons.Default.Description,
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredBio(it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                Text(
                    text = stringResource(id = R.string.select_top_3_skills),
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(SpaceLarge))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    mainAxisAlignment = MainAxisAlignment.Center,
                    mainAxisSpacing = SpaceMedium,
                    crossAxisSpacing = SpaceMedium
                ) {
                    viewModel.skills.value.skills.forEach { currentSkill ->
                        Chip(
                            text = currentSkill.name,
                            selected = viewModel.skills.value.selectedSkills.any {
                                                         it.name == currentSkill.name
                            },
                            onChipClick = {
                                viewModel.onEvent(EditProfileEvent.     SetSkillSelected(currentSkill))

                            }
                        )
                    }

                }
            }

        }

    }
}


@Composable
fun BannerEditSection(
    bannerImage: Painter,
    profileImage: Painter,
    profilePictureSize: Dp = ProfilePictureSizeLage,
    onBannerClick: () -> Unit = {},
    onProfilePictureClick: () -> Unit = {}

) {

    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(bannerHeight + profilePictureSize / 2f)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(bannerHeight)
                .clickable { onBannerClick() },
            contentScale = ContentScale.Crop,
            painter = bannerImage,
            contentDescription = null
        )
        Image(
            painter = profileImage,
            contentDescription = null,
            modifier = Modifier
                //the height of the entire box is defined above so if you align at bottom center
                //you will have the desired position.
                .align(Alignment.BottomCenter)
                .size(profilePictureSize)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface,
                    shape = CircleShape

                )
                .clickable { onProfilePictureClick() }
        )
    }

}





















