package com.robertconstantindinescu.my_social_network.presentation.util

import android.provider.ContactsContract
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.robertconstantindinescu.my_social_network.presentation.MainFeedScreen
import com.robertconstantindinescu.my_social_network.presentation.activity.ActivityScreen
import com.robertconstantindinescu.my_social_network.presentation.chat.ChatScreen
import com.robertconstantindinescu.my_social_network.presentation.create_post.CreatePostScreen
import com.robertconstantindinescu.my_social_network.presentation.splash.SplashScreen
import com.robertconstantindinescu.my_social_network.presentation.login.LoginScreen
import com.robertconstantindinescu.my_social_network.presentation.profile.ProfileScreen
import com.robertconstantindinescu.my_social_network.presentation.register.RegisterScreen

@Composable
fun Navigation(
    navController: NavHostController
) {
    //val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ){
        composable(route= Screen.SplashScreen.route){
            //here goes the composable that comes up when visit the route
            SplashScreen(navController = navController)

        }
        composable(route = Screen.LoginScreen.route){
            LoginScreen(navController = navController)
        }
        composable(Screen.RegisterScreen.route){
            RegisterScreen(navController = navController)
        }
        //Bottom
        composable(Screen.MainFeedScreen.route){
            MainFeedScreen(navController = navController)
        }

        composable(route = Screen.ChatScreen.route){
            ChatScreen(navController = navController)
        }
        composable(route = Screen.ActivityScreen.route){
            ActivityScreen(navController = navController)
        }
        composable(route = Screen.ProfileScreen.route){
            ProfileScreen(navController = navController)
        }

        composable(route = Screen.CreatePostScreen.route){
            CreatePostScreen(navController = navController)
        }

    }
}