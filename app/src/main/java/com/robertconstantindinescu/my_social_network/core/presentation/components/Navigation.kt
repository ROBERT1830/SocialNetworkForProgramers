package com.robertconstantindinescu.my_social_network.presentation.util

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.ImageLoader
import com.robertconstantindinescu.my_social_network.presentation.MainFeedScreen
import com.robertconstantindinescu.my_social_network.feature_activity.presentation.ActivityScreen
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
    scaffoldState: ScaffoldState,
    imageLoader: ImageLoader
) {
    //val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(route = Screen.SplashScreen.route) {
            //here goes the composable that comes up when visit the route
            SplashScreen(
                onPopBackStack = navController::popBackStack,
                onNavigate = navController::navigate,
            )

        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(
                onNavigate = navController::navigate,
                scaffoldState = scaffoldState
            )
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController, scaffoldState = scaffoldState)
        }
        //Bottom
        composable(Screen.MainFeedScreen.route) {
            MainFeedScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate //directly the route are passed
                , scaffoldState = scaffoldState,
                imageLoader = imageLoader)
        }

        composable(route = Screen.ChatScreen.route) {
            ChatScreen( onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate ,//directly the route are passed )
                imageLoader = imageLoader)
        }
        composable(route = Screen.ActivityScreen.route) {
            ActivityScreen(onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate)
        }
        composable( // the "?" is because it can be null the argument
            route = Screen.ProfileScreen.route + "?userId={userId}",
            arguments = listOf(
                navArgument(
                    name = "userId"
                ){
                    type = NavType.StringType
                    nullable = true //the argument could be null in case we want to access our own profile
                    defaultValue = null
                }
            )
        ) {
            //val userId = it.arguments?.getString("userId")
            /**
             * The cool thing about savedStateHandle is that we just register th enavigation argument
             * in the backStack. And we just need to pass it and automatically will be contained
             * in the savedStateHandle.
             * So in the viewmdeol we get the navigation argument without actually needed to pass it
             * to the profile screen. We just get the bundle from specifc screen in the viewmodel.
             * savedStateHandle is a tool to restore the state of the viewModel and it also contains
             * the nav arguments.
             */
            ProfileScreen(
                userId = it.arguments?.getString("userId"),
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                scaffoldState = scaffoldState,//directly the route are passed  /*userId = userId*/, )
                imageLoader = imageLoader
            )

        }
        composable(
            //here the user id is not optional. I must be passed. So do it like that.
            route = Screen.EditProfileScreen.route + "/{userId}",
            arguments = listOf(
                navArgument(
                    name = "userId"
                ){
                    type = NavType.StringType
                    nullable = true //the argument could be null in case we want to access our own profile
                    defaultValue = null
                }
            )
        ) {
            EditProfileScreen(onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate, scaffoldState = scaffoldState,
                imageLoader = imageLoader
            )
        }

        composable(route = Screen.CreatePostScreen.route) {
            CreatePostScreen(onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate, scaffoldState = scaffoldState,
                imageLoader = imageLoader)
        }
        composable(route = Screen.SearchScreen.route) {
            SearchScreen(onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader)
        }

        composable(
            route = Screen.PersonalListScreen.route +"/{parentId}",
            arguments = listOf(
                navArgument("parentId"){
                    type = NavType.StringType
                }
            )
        ){
            PersonListScreen(onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate, scaffoldState = scaffoldState,
                imageLoader = imageLoader)
        }

        composable(
            route = Screen.PostDetailScreen.route + "/{postId}?shouldShowKeyBoard={shouldShowKeyBoard}",
            arguments = listOf(
                navArgument(
                    name = "postId"
                ){
                    type = NavType.StringType
                },
                //this is for when you clik on comment from item post and goes directly to the details post and opens the keyboard.
                //That happens from the mainFeedscreen
                navArgument(
                    name = "shouldShowKeyboard"
                ) {
                    type = NavType.BoolType
                    defaultValue = false
                }


            )
        ) {
            val shouldShowKeyboard = it.arguments?.getBoolean("shouldShowKeyBoard") ?: false
            PostDetailScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                scaffoldState =  scaffoldState,
                shouldShowKeyboard = shouldShowKeyboard,
                imageLoader = imageLoader
            )
        }

    }
}