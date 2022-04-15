package com.robertconstantindinescu.my_social_network.presentation.post_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.domain.models.Post
import com.robertconstantindinescu.my_social_network.presentation.components.ActionRow
import com.robertconstantindinescu.my_social_network.presentation.components.Post
import com.robertconstantindinescu.my_social_network.presentation.components.StandardToolBar
import com.robertconstantindinescu.my_social_network.presentation.ui.theme.*
import com.robertconstantindinescu.my_social_network.presentation.util.Screen
import com.robertconstantindinescu.my_social_network.util.Constants.MAX_POST_DESCRIPTION_LINES

import com.robertconstantindinescu.my_social_network.domain.models.Comment

@Composable
fun PostDetailScreen(
    navController: NavController,
    //here we will need to pass a post id to take the entire info from backend. 
    //but for now, just pass full object
    post: Post
) {

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        StandardToolBar(
            navController = navController,
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
                .fillMaxSize()
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
                                .offset(y = ProfilePictureSize / 2f)
                                .clip(MaterialTheme.shapes.medium)
                                .shadow(5.dp)
                                .background(MediumGray)

                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.kermit),
                                contentDescription = "Post Image",
                                modifier = Modifier.fillMaxWidth()
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(SpaceLarge)
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
                                    text = post.description,
                                    style = MaterialTheme.typography.body2

                                )
                                Spacer(modifier = Modifier.height(SpaceMedium))

                                Text(
                                    text = stringResource(
                                        id = R.string.liked_by_x_people,
                                        post.likeCount
                                    ),
                                    style = MaterialTheme.typography.body2,
                                    fontWeight = FontWeight.Bold

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


                        Image(
                            painter = painterResource(id = R.drawable.robert),
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .size(ProfilePictureSize)
                                .clip(CircleShape)
                                .align(Alignment.TopCenter)

                        )

                    }

                }
                Spacer(modifier = Modifier.height(SpaceLarge))

            }
            items(20) {
                Comment(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = SpaceMedium,
                            vertical = SpaceSmall
                        ),
                    comment = Comment(
                        username = "Robert Constantin",
                        comment = "Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go Lorem ismspu fonr  dgth amigh go  "
                    )
                )
            }
        }
    }


}


@Composable
fun Comment(
    modifier: Modifier = Modifier,
    comment: Comment = Comment(),
    onLikeClick: (Boolean) -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = 5.dp,
        shape = MaterialTheme.shapes.medium

    ) {
        Column(
            modifier = modifier
                .fillMaxSize()


        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.robert),
                        contentDescription = "",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(ProfilePictureSizeSmall)
                    )
                    Spacer(modifier = Modifier.width(SpaceSmall))
                    Text(
                        text = comment.username,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground
                    )
                }
                Text(
                    text = "2 days ago",
                    style = MaterialTheme.typography.body2,
                )
            }
            Spacer(modifier = Modifier.height(SpaceSmall))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = comment.comment,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.weight(0.9f)
                )
                Spacer(modifier = Modifier.width(SpaceSmall))
                IconButton(
                    modifier = Modifier.weight(0.1f),
                    onClick = {
                        onLikeClick(comment.isLiked)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = if (comment.isLiked) {
                            stringResource(id = R.string.unlike)
                        } else stringResource(id = R.string.like),
                        tint = if (comment.isLiked) {
                            Color.Red
                        } else {
                            TextWhite
                        }
                    )

                }
            }
            Spacer(modifier = Modifier.height(SpaceMedium))
            Text(
                text = stringResource(
                    id = R.string.liked_by_x_people,
                    comment.likeCount,
                ),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground
            )
        }

    }

}















































