package com.robertconstantindinescu.my_social_network.presentation.util

sealed class Screen(val route: String) {
    //define as objects we don't pass parameters to it
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object MainFeedScreen : Screen("main_feed_screen")
    object PostDetailScreen : Screen("post_detail_screen")
    object ChatScreen : Screen("chat_screen")
    object MessagesScreen : Screen("messages_screen")
    object EditProfileScreen : Screen("edi_profile_screen")
    object ProfileScreen : Screen("profile_screen")
    object PersonalListScreen : Screen("personal_list_screen")
    object CreatePostScreen : Screen("create_post_screen")
    object ActivityScreen : Screen("activity_screen")
    object SearchScreen : Screen("search_screen")
}

