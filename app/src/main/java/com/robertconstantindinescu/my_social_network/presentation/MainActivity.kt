package com.robertconstantindinescu.my_social_network.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.presentation.components.StandardScaffold
import com.robertconstantindinescu.my_social_network.presentation.ui.theme.My_social_networkTheme
import com.robertconstantindinescu.my_social_network.presentation.util.Navigation
import com.robertconstantindinescu.my_social_network.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            My_social_networkTheme {


                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()

                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    StandardScaffold(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                        //if current route is inside this list then show bottom bar
                        showBottomBar = navBackStackEntry?.destination?.route in listOf(
                            Screen.MainFeedScreen.route,
                            Screen.ChatScreen.route,
                            Screen.ActivityScreen.route,
                            Screen.ProfileScreen.route,
                        ),
//                        showBackArrow = navBackStackEntry?.destination?.route in listOf(
//                            Screen.PostDetailScreen.route,
//                            Screen.MessagesScreen.route,
//                            Screen.EditProfileScreen.route,
//                            Screen.SearchScreen.route,
//                            Screen.CreatePostScreen.route,
//                            Screen.PersonalListScreen.route,
//                        ),
//                        toolBarTitle = when (navBackStackEntry?.destination?.route) {
//                            Screen.MainFeedScreen.route, Screen.PostDetailScreen.route -> {
//                                stringResource(id = R.string.)
//                            }
//                        },
                        onFabClick = {
                            navController.navigate(Screen.CreatePostScreen.route)
                        }
                    ) {
                        Navigation(navController = navController)
                    }


                }
            }
        }
    }
}

