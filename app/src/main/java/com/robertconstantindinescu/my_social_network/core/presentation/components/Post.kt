package com.robertconstantindinescu.my_social_network.core.presentation.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.*
import com.robertconstantindinescu.my_social_network.core.util.Constants.MAX_POST_DESCRIPTION_LINES

@Composable
fun Post(
    modifier: Modifier = Modifier,
    post: Post,
    showProfileImage: Boolean = true,
    onPostClick: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SpaceMedium)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = if (showProfileImage) ProfilePictureSizeMedium / 2f else 0.dp)
                .shadow(5.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MediumGray)
                .clickable {
                    onPostClick()
                }
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = post.imageUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            //this is for debuging the coil response
                            listener(
                                onStart = { _ ->
                                    println("START LOADING IMAGE")
                                },
                                onCancel = {
                                    println("REQUEST CANCELLED")

                                },
                                // t is a throwable
                                onError = { _, t ->
                                    println("ERROR LOADING IMAGE")
                                    t.throwable.printStackTrace()
                                }
                            )
                            crossfade(true)
                        }).build()
                ),
                contentDescription = "Post Image"
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceMedium)
            ) {
                ActionRow(
                    username = "Robert Constantin",
                    modifier = Modifier.fillMaxWidth(),
                    onLikeClick = {

                    },
                    onCommentClick = {

                    },
                    onShareClick = {

                    },
                    onUsernameClick = {

                    }
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                Text(
                    text = buildAnnotatedString {
                        append(post.description)
                        withStyle(SpanStyle(color = HintGray)) {
                            append(LocalContext.current.getString(R.string.read_more))
                        }
                    },
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = MAX_POST_DESCRIPTION_LINES

                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.liked_by_x_people, post.likeCount),
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.h2,
                        fontWeight = FontWeight.Bold

                        )
                    Text(
                        text = stringResource(id = R.string.x_comments, post.commentCount),
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.h2,
                        fontWeight = FontWeight.Bold

                        )

                }
            }
        }


        if (showProfileImage){
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = post.profilePicture)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(ProfilePictureSizeMedium)
                    .clip(CircleShape)
                    .align(Alignment.TopCenter)


            )
        }


    }

}


@Composable
fun EngagementButtons(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    iconSize: Dp = 30.dp,
    onLikeClick: (Boolean) -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                onLikeClick(!isLiked)
            },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                tint = if (isLiked) {
                    Color.Red
                } else {
                    TextWhite
                },
                contentDescription = if (isLiked) {
                    stringResource(id = R.string.like)
                } else {
                    stringResource(id = R.string.unlike)
                }
            )

        }
        Spacer(modifier = Modifier.width(SpaceMedium))
        IconButton(
            onClick = {
                onCommentClick()
            },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Comment, contentDescription = if (isLiked) {
                    stringResource(id = R.string.comment)
                } else {
                    stringResource(id = R.string.uncomment)
                }
            )

        }
        Spacer(modifier = Modifier.width(SpaceMedium))
        IconButton(
            onClick = {
                onShareClick()
            }, modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Share, contentDescription = if (isLiked) {
                    stringResource(id = R.string.share)
                } else {
                    stringResource(id = R.string.unshare)
                }
            )

        }

    }

}

//name
@Composable
fun ActionRow(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    onLikeClick: (Boolean) -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    username: String,
    onUsernameClick: (String) -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = username,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary
            ),
            modifier = Modifier.clickable {
                onUsernameClick(username)
            }
        )
        EngagementButtons(
            isLiked = isLiked,
            onLikeClick = onLikeClick,
            onCommentClick = onCommentClick,
            onShareClick = onShareClick
        )

    }

}































