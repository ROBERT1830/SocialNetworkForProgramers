package com.robertconstantindinescu.my_social_network.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.presentation.components.Post
import com.robertconstantindinescu.my_social_network.presentation.components.StandardToolBar
import com.robertconstantindinescu.my_social_network.core.util.Screen

@Composable
fun MainFeedScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
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
            showBackArrow = true,
            navAction = {
                IconButton(onClick = {
                    navController.navigate(Screen.SearchScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Add",
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        )
        Post(
            post = com.robertconstantindinescu.my_social_network.core.domain.models.Post(
                username = "Robert Constantin",
                imageUrl = "",
                profilePicture = "",
                description = "sadasd asdasd asfsd fsd g sdg sd g sf gfs g df gdf g dsf gs dfg " +
                        "sdfgsdfgsdfgsdfg sdfg sdfg aerhwet hd b",
                likeCount = 17,
                commentCount = 7
            ),
            onPostClick = {
                navController.navigate(Screen.PostDetailScreen.route)
            }
        )
    }


}