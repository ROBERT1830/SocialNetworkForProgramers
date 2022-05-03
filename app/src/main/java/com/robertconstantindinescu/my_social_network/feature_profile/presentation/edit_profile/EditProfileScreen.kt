package com.robertconstantindinescu.my_social_network.feature_profile.presentation.edit_profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.presentation.components.StandardTextField
import com.robertconstantindinescu.my_social_network.core.presentation.components.StandardToolBar
import com.robertconstantindinescu.my_social_network.feature_profile.presentation.edit_profile.components.Chip
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.ProfilePictureSizeLage
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceLarge
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceMedium
import com.robertconstantindinescu.my_social_network.core.domain.states.StandardTextFieldState
import com.robertconstantindinescu.my_social_network.feature_profile.presentation.util.EditProfileError
import kotlin.random.Random


@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileScreenViewModel = hiltViewModel(),
    profilePictureSize: Dp = ProfilePictureSizeLage

) {


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StandardToolBar(
            navController = navController,
            showBackArrow = true,
            navAction = {
                IconButton(onClick = { }) {
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
                bannerImage = painterResource(id = R.drawable.channelart),
                profileImage = painterResource(
                    id = R.drawable.robert
                ),
                profilePictureSize = profilePictureSize
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
                        viewModel.setUsernameState(
                            StandardTextFieldState(text = it)
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
                        viewModel.setGithubTextFiledState(
                            StandardTextFieldState(text = it)
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
                        viewModel.setInstagramTextFieldState(
                            StandardTextFieldState(text = it)
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
                        viewModel.setLinkedInTextFieldState(
                            StandardTextFieldState(text = it)
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
                        viewModel.setBioState(
                            StandardTextFieldState(text = it)
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
                    listOf(
                        "Kotlin",
                        "JavaScript",
                        "Assembly",
                        "C++",
                        "C#",
                        "Python",
                        "Dart",
                        "Java",
                        "C"

                    ).forEach {
                        Chip(
                            text = it,
                            selected = Random.nextInt(2) == 0
                        ) {

                        }
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
    onProfileImageClick: () -> Unit = {}

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
                .height(bannerHeight),
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
        )
    }

}





















