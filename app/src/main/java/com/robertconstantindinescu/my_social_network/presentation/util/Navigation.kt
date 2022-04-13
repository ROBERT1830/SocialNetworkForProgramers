package com.robertconstantindinescu.my_social_network.presentation.util

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.robertconstantindinescu.my_social_network.presentation.MainFeedScreen
import com.robertconstantindinescu.my_social_network.presentation.splash.SplashScreen
import com.robertconstantindinescu.my_social_network.presentation.login.LoginScreen
import com.robertconstantindinescu.my_social_network.presentation.register.RegisterScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
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
        composable(Screen.MainFeedScreen.route){
            MainFeedScreen(navController = navController)
        }



    }
}