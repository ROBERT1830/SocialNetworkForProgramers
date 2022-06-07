package com.robertconstantindinescu.my_social_network.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.domain.models.User
import com.robertconstantindinescu.my_social_network.core.domain.models.UserItem
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.*

@ExperimentalMaterialApi
@Composable
fun UserProfileItem(
    user: UserItem,
    modifier: Modifier = Modifier,
    actionIcon: @Composable () -> Unit = {},
    onItemClick: () -> Unit = {},
    onActionItemClick: () -> Unit = {},
     ownUserId: String = ""
) {


    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = onItemClick,
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = SpaceSmall, horizontal = SpaceMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = rememberImagePainter(
                    data = user.profilePictureUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(ProfilePictureSizeSmall)
                    .clip(CircleShape)


            )
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = SpaceSmall)
                .weight(1f)
                ) {
                Text(
                    text = user.userName,
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(SpaceSmall))
                Text(
                    text = user.bio,
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )

            }
            //this only should exist if the user is now our own user
            //we can do a state in the viewmdoel that says it this is oursrelfs or not.
            //do a use case getOwnUserId
            if (user.userId != ownUserId){
                IconButton(
                    onClick = { onActionItemClick() },
                    modifier = Modifier.size(IconSizeMedium)
                ) {
                    actionIcon()
                }
            }

        }


    }
}