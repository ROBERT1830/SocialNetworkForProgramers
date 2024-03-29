package com.robertconstantindinescu.my_social_network.feature_profile.presentation.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.domain.models.User
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceMedium
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceSmall

@Composable
fun ProfileHeaderSection(
    user: User,
    modifier: Modifier = Modifier,
    isOwnProfile: Boolean = true,
    isFollowing: Boolean = true,
    onAddEditClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},

) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
                //offset does not change the bounding of that box
            //.offset(y = -ProfilePictureSizeLage / 2f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            //combine spacer and icon size
            modifier = Modifier.offset(
                x=
                if (isOwnProfile) {
                    (30.dp +  SpaceSmall) / 2f
                }else 0.dp
            )


        ) {
            Text(
                text = user.username,
                style = MaterialTheme.typography.h1.copy(fontSize = 24.sp),
                textAlign = TextAlign.Center
            )
            if (isOwnProfile){
                Spacer(modifier = Modifier.width(SpaceSmall))
                IconButton(onClick = { onAddEditClick()},
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = stringResource(id = R.string.edit))

                }
                Spacer(modifier = Modifier.width(SpaceSmall))
                IconButton(
                    onClick = onLogoutClick,
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = stringResource(id = R.string.logout)
                    )
                }
            }


        }
        Spacer(modifier = Modifier.height(SpaceMedium))
        if (user.bio.isNotBlank()){
            Text(
                text = user.bio,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
        }


        ProfileStats(user = user, isOwnProfile = isOwnProfile, isFollowing = isFollowing)




    }

}