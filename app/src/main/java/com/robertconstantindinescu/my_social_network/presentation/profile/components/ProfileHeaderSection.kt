package com.robertconstantindinescu.my_social_network.presentation.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.domain.models.User
import com.robertconstantindinescu.my_social_network.presentation.ui.theme.ProfilePictureSizeLage
import com.robertconstantindinescu.my_social_network.presentation.ui.theme.ProfilePictureSizeMedium
import com.robertconstantindinescu.my_social_network.presentation.ui.theme.SpaceMedium
import com.robertconstantindinescu.my_social_network.presentation.ui.theme.SpaceSmall
import okhttp3.internal.cache2.Relay.Companion.edit

@Composable
fun ProfileHeaderSection(
    user: User,
    modifier: Modifier = Modifier,
    isOwnProfile: Boolean = true,
    onAddEditClick: () -> Unit = {}

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
            }


        }
        Spacer(modifier = Modifier.height(SpaceMedium))
        Text(
            text = user.description,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(SpaceMedium))
        ProfileStats(user = user, isOwnProfile = isOwnProfile)




    }

}