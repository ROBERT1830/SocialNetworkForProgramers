package com.robertconstantindinescu.my_social_network.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.domain.models.User
import com.robertconstantindinescu.my_social_network.core.presentation.components.StandardToolBar
import com.robertconstantindinescu.my_social_network.core.presentation.components.UserProfileItem
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.IconSizeMedium
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceLarge
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceMedium

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PersonListScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolBar(
            navController = navController,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(id = R.string.liked_by),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground

                )
            }
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(SpaceLarge)
        ){
            items(10){
                UserProfileItem(
                    user = User(
                        profilePicture = "",
                        username = "Robert Constantin",
                        description = "Lorem ipsum es el texto que se usa habitualmente en diseño " +
                                "gráfico en demostraciones de tipografías o de borradores de diseño " +
                                "para probar el diseño visual antes de insertar el texto final",
                        followerCount = 234,
                        followingCount = 432,
                        postCount = 23
                    ),
                    actionIcon = {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = null,
                            modifier = Modifier.size(IconSizeMedium)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
            }
        }
    }
}