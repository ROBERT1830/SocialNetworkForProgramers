package com.robertconstantindinescu.my_social_network.feature_post.presentation.post_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.domain.models.Comment
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.ProfilePictureSizeExtraSmall
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceSmall

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    comment: Comment, //mandatory argument
    onLikeClick: (Boolean) -> Unit = {},
    onLikedByClick: () -> Unit = {}
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
                        painter = rememberImagePainter(
                            data = comment.profilePictureUrl,
                            imageLoader = imageLoader
                        ),
                        contentDescription = "",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(ProfilePictureSizeExtraSmall)
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
                    text = comment.formattedTime,
                    style = MaterialTheme.typography.body2,
                )
            }
            Spacer(modifier = Modifier.height(SpaceSmall))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Column(modifier = Modifier.weight(9f)) {
                    Text(
                        text = comment.comment,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(SpaceSmall))
                    Text(
                        text = stringResource(
                            id = R.string.x_likes,
                            comment.likeCount,
                        ),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.fillMaxWidth()
                            .clickable {
                                onLikedByClick()
                            }
                    )
                }

                Spacer(modifier = Modifier.width(SpaceSmall))
                IconButton(
                    modifier = Modifier.weight(0.1f),
                    onClick = {
                        onLikeClick(comment.isLiked)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        tint = if (comment.isLiked) {
                            MaterialTheme.colors.primary
                        } else MaterialTheme.colors.onBackground,
                        contentDescription = if (comment.isLiked) {
                            stringResource(id = R.string.unlike)
                        } else stringResource(id = R.string.like),
//                        tint = if (comment.isLiked) {
//                            Color.Red
//                        } else {
//                            TextWhite
//                        }
                    )

                }
            }
        }

    }

}