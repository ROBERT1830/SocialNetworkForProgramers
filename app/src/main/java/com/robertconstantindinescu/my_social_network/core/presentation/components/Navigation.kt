package com.robertconstantindinescu.my_social_network.presentation.util

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.robertconstantindinescu.my_social_network.presentation.MainFeedScreen
import com.robertconstantindinescu.my_social_network.feature_activity.presentation.activity.ActivityScreen
import com.robertconstantindinescu.my_social_network.feature_chat.presentation.chat.ChatScreen
import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import com.robertconstantindinescu.my_social_network.core.util.Screen
import com.robertconstantindinescu.my_social_network.presentation.PersonListScreen
import com.robertconstantindinescu.my_social_network.feature_post.presentation.create_post.CreatePostScreen
import com.robertconstantindinescu.my_social_network.feature_profile.presentation.edit_profile.EditProfileScreen
import com.robertconstantindinescu.my_social_network.feature_auth.presentation.login.LoginScreen
import com.robertconstantindinescu.my_social_network.feature_post.presentation.post_detail.PostDetailScreen
import com.robertconstantindinescu.my_social_network.feature_profile.presentation.profile.ProfileScreen
import com.robertconstantindinescu.my_social_network.feature_auth.presentation.register.RegisterScreen
import com.robertconstantindinescu.my_social_network.feature_auth.presentation.splash.SplashScreen
import com.robertconstantindinescu.my_social_network.feature_profile.presentation.search.SearchScreen

@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState
) {
    //val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(route = Screen.SplashScreen.route) {
            //here goes the composable that comes up when visit the route
            SplashScreen(navController = navController)

        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController, scaffoldState = scaffoldState)
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController, scaffoldState = scaffoldState)
        }
        //Bottom
        composable(Screen.MainFeedScreen.route) {
            MainFeedScreen(navController = navController)
        }

        composable(route = Screen.ChatScreen.route) {
            ChatScreen(navController = navController)
        }
        composable(route = Screen.ActivityScreen.route) {
            ActivityScreen(navController = navController)
        }
        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(route = Screen.EditProfileScreen.route) {
            EditProfileScreen(navController = navController)
        }

        composable(route = Screen.CreatePostScreen.route) {
            CreatePostScreen(navController = navController)
        }
        composable(route = Screen.SearchScreen.route) {
            SearchScreen(navController = navController)
        }
        
        composable(route = Screen.PersonalListScreen.route){
            PersonListScreen(navController = navController)
        }

        composable(route = Screen.PostDetailScreen.route) {
            PostDetailScreen(
                navController = navController,
                post = Post(
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

    }
}