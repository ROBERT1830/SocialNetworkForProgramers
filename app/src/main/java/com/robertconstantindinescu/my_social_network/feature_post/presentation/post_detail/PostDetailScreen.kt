package com.robertconstantindinescu.my_social_network.feature_post.presentation.post_detail

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.presentation.components.ActionRow
import com.robertconstantindinescu.my_social_network.core.presentation.components.StandardToolBar
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.*

import com.robertconstantindinescu.my_social_network.core.presentation.components.StandardTextField
import com.robertconstantindinescu.my_social_network.core.presentation.util.UiEvent
import com.robertconstantindinescu.my_social_network.core.presentation.util.asString
import com.robertconstantindinescu.my_social_network.core.util.Screen
import com.robertconstantindinescu.my_social_network.core.util.showKeyboard
import com.robertconstantindinescu.my_social_network.feature_profile.presentation.profile.ProfileScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PostDetailScreen(
    scaffoldState: ScaffoldState,
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    //here we will need to pass a post id to take the entire info from backend. 
    //but for now, just pass full object
    viewModel: PostDetailViewModel = hiltViewModel(),
    shouldShowKeyboard: Boolean = false
) {

    val state = viewModel.state.value
    val commentTextFieldState = viewModel.commentTextFieldState.value

    val context = LocalContext.current

    val focusRequester = remember {
        FocusRequester()
    }
    LaunchedEffect(key1 = true){
        if (shouldShowKeyboard){
            context.showKeyboard()
            focusRequester.requestFocus()
        }
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        StandardToolBar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = stringResource(id = R.string.your_feed),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground

                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colors.surface)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background)
                ) {

                    //if (scrollState.value == 0) {
                    Spacer(
                        modifier = Modifier
                            .height(SpaceLarge)
                    )
                    //}

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                        //.verticalScroll(scrollState)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .offset(y = ProfilePictureSizeExtraSmall / 2f)
                                .clip(MaterialTheme.shapes.medium)
                                .shadow(5.dp)
                                .background(MediumGray)

                        ) {
                            state.post?.let { post ->
                                Image(
                                    painter = rememberImagePainter(
                                        data = state.post.imageUrl,
                                        imageLoader = imageLoader
                                    ),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "Post Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(16f / 9f) //this is the ratio for the images
                                )

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(SpaceLarge)
                                ) {
                                    ActionRow(
                                        username = state.post.username,
                                        modifier = Modifier.fillMaxWidth(),
                                        onLikeClick = {
                                            viewModel.onEvent(PostDetailEvent.LikePost)
                                        },
                                        onCommentClick = {

                                        },
                                        onShareClick = {
                                            context.showKeyboard()
                                            focusRequester.requestFocus()

                                        },
                                        onUsernameClick = {
                                            onNavigate(Screen.ProfileScreen.route + "?userId=${post.userId}") //if you pass a mandatory argument you will have this error .....cannot be found i
                                        },
                                        isLiked = state.post.isLiked
                                    )
                                    Spacer(modifier = Modifier.height(SpaceMedium))
                                    Text(
                                        text = post.description,
                                        style = MaterialTheme.typography.body2

                                    )
                                    Spacer(modifier = Modifier.height(SpaceMedium))

                                    Text(
                                        text = stringResource(
                                            id = R.string.x_likes,
                                            post.likeCount
                                        ),
                                        style = MaterialTheme.typography.body2,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.clickable {
                                            onNavigate(Screen.PersonalListScreen.route + "/${post.id}")
                                        }

                                    )

//                    Lazy
//
//                    for (i in 1..5) {
//                        //items(20) {
//                            Comment(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(
//                                        vertical = SpaceSmall
//                                    ),
//                                comment = Comment(
//                                    username = "Robert Constantin$i",
//                                    comment = "Lorem ismspu fonr  dgth amigh go to to ehe hg bebsad an " +
//                                            "tou  madhter ai ogu to fucj s¡ my lieev"
//                                )
//                            )
//                       // }
//
//                    }

                                }


                            }


                        }
                        Image(
                            painter = rememberImagePainter(
                                data = state.post?.profilePicture,
                                imageLoader = imageLoader
                            ),
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .size(ProfilePictureSizeExtraSmall)
                                .clip(CircleShape)
                                .align(Alignment.TopCenter)

                        )
                        if (state.isLoadingPost) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }


                    }

                }
                Spacer(modifier = Modifier.height(SpaceLarge))

            }
            items(state.comments) { comment ->
                Comment(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = SpaceMedium,
                            vertical = SpaceSmall
                        ),
                    imageLoader = imageLoader,
                    comment = comment,
                    onLikeClick = {
                        viewModel.onEvent(PostDetailEvent.LikeComment(comment.commentId))
                    },
                    onLikedByClick = {
                        onNavigate(Screen.PersonalListScreen.route + "/${comment.commentId}")
                    }
//                    comment = Comment(
//                        username = "Robert Constantin",
//                        comment = "Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go  "
//                    )
                )
            }
        }
        Row(modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth()
            .padding(SpaceLarge),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StandardTextField(
                text = commentTextFieldState.text,
                onValueChange = {
                    viewModel.onEvent(PostDetailEvent.EnteredComment(it))
                },
                backgroundColor = MaterialTheme.colors.background,
                modifier = Modifier
                    .weight(1f),
                hint = stringResource(id = R.string.enter_a_comment)
            )
            //we dont need to pas the comment and build a data class for the state because we have the
            //comment in our state nad we have the state in the viewmodel. We can just refer to the
            //textfieldState of the viewmodel

            if (viewModel.commentState.value.isLoading){
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .size(24.dp),
                    strokeWidth = 2.dp
                )
            }else{
                IconButton(
                    onClick = {viewModel.onEvent(PostDetailEvent.Comment)},
                    enabled = commentTextFieldState.error == null
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        tint = if (commentTextFieldState.error == null ){
                            MaterialTheme.colors.primary
                        }else MaterialTheme.colors.background,
                        contentDescription = stringResource(id = R.string.send_comment)
                    )

                }
            }

        }

    }


}


















































