package com.robertconstantindinescu.my_social_network.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.domain.models.User
import com.robertconstantindinescu.my_social_network.core.domain.models.UserItem
import com.robertconstantindinescu.my_social_network.core.presentation.components.StandardToolBar
import com.robertconstantindinescu.my_social_network.core.presentation.components.UserProfileItem
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.IconSizeMedium
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceLarge
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceMedium
import com.robertconstantindinescu.my_social_network.core.presentation.util.UiEvent
import com.robertconstantindinescu.my_social_network.core.presentation.util.asString
import com.robertconstantindinescu.my_social_network.core.util.Screen
import com.robertconstantindinescu.my_social_network.feature_post.presentation.person_list.PersonListEvent
import com.robertconstantindinescu.my_social_network.feature_post.presentation.person_list.PersonListViewModel
import kotlinx.coroutines.flow.collectLatest
import okhttp3.internal.notifyAll
import okhttp3.internal.userAgent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PersonListScreen(
    scaffoldState: ScaffoldState,
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: PersonListViewModel = hiltViewModel(),

) {
    val state1 = viewModel.state.value
    val state = state1
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolBar(
            onNavigateUp = { onNavigateUp() },
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(id = R.string.liked_by),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground

                )
            }
        )
        //top show the snackbar
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(SpaceLarge)
            ) {
                items(state.users) { user ->
                    UserProfileItem(
                        user = user,
                        imageLoader = imageLoader,
                        actionIcon = {
                            Icon(
                                //if we are following the user then the followiong icon is person remove
                                imageVector = if (user.isFollowing) {
                                    Icons.Default.PersonRemove
                                } else Icons.Default.PersonAdd,
                                contentDescription = null,
                                modifier = Modifier.size(IconSizeMedium)
                            )
                        },
                        onItemClick = {
                            onNavigate(Screen.ProfileScreen.route + /*"/${user.userId}"*/ "?userId=${user.userId}") //mandatory argument commented
                        },
                        onActionItemClick = {
                            viewModel.onEvent(PersonListEvent.ToggleFollowStateForUser(user.userId))
                        },
                        ownUserId = viewModel.ownUserId.value
                    )
                    Spacer(modifier = Modifier.height(SpaceMedium))
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }


    }
}