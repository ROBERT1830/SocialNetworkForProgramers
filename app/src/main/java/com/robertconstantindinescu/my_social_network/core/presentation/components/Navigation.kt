package com.robertconstantindinescu.my_social_network.presentation.util

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
            SplashScreen(
                onPopBackStack = navController::popBackStack,
                onNavigate = navController::navigate
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
                , scaffoldState = scaffoldState)
        }

        composable(route = Screen.ChatScreen.route) {
            ChatScreen( onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate )//directly the route are passed )
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
            ProfileScreen( onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                scaffoldState = scaffoldState)//directly the route are passed  /*userId = userId*/, )
        }
        composable(route = Screen.EditProfileScreen.route) {
            EditProfileScreen(onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate)
        }

        composable(route = Screen.CreatePostScreen.route) {
            CreatePostScreen(onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate, scaffoldState = scaffoldState)
        }
        composable(route = Screen.SearchScreen.route) {
            SearchScreen(onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate)
        }
        
        composable(route = Screen.PersonalListScreen.route){
            PersonListScreen(onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate)
        }

        composable(route = Screen.PostDetailScreen.route) {
            PostDetailScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
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