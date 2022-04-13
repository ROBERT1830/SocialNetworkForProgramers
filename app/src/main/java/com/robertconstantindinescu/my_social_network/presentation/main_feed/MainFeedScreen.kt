package com.robertconstantindinescu.my_social_network.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.robertconstantindinescu.my_social_network.presentation.components.Post
import com.robertconstantindinescu.my_social_network.presentation.components.StandardScaffold

@Composable
fun MainFeedScreen(
    navController: NavController
) {

    Post(post = com.robertconstantindinescu.my_social_network.domain.models.Post(
        username = "Robert Constantin",
        imageUrl = "",
        profilePicture = "",
        description = "sadasd asdasd asfsd fsd g sdg sd g sf gfs g df gdf g dsf gs dfg " +
                "sdfgsdfgsdfgsdfg sdfg sdfg aerhwet hd b",
        likeCount = 17,
        commentCount = 7
    )
    )




}