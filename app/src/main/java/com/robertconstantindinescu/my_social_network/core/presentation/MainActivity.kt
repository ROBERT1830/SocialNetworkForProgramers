package com.robertconstantindinescu.my_social_network.core.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.robertconstantindinescu.my_social_network.core.presentation.components.StandardScaffold
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.My_social_networkTheme
import com.robertconstantindinescu.my_social_network.presentation.util.Navigation
import com.robertconstantindinescu.my_social_network.core.util.Screen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var imageLoader: ImageLoader
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
                    val scaffoldState = rememberScaffoldState()
                    //**********************
//                    val lifecycleOwner = LocalLifecycleOwner.current
//                    DisposableEffect(
//                        key1 = lifecycleOwner,
//                        effect = {
//                            val observer = LifecycleEventObserver { _, event ->
//                                if (event == Lifecycle.Event.ON_CREATE){
//                                    getPostIdFromDeepLink(intent)?.let { postId ->
//                                        navController.navigate(
//                                            Screen.PostDetailScreen.route + "$postId"
//                                        )
//
//                                    }
//                                }
//                            }
//                            lifecycleOwner.lifecycle.addObserver(observer)
//                            onDispose {
//                                lifecycleOwner.lifecycle.removeObserver(observer)
//                            }
//                        }
//                    )
                    //**********************
                    StandardScaffold(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                        //if current route is inside this list then show bottom bar
                        showBottomBar = shouldShowBottomBar(navBackStackEntry),
                        state = scaffoldState,
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
                        Navigation(navController = navController, scaffoldState = scaffoldState, imageLoader)
                    }


                }
            }
        }
    }

    //this will be called efvery time is called a new intent. And we now we need the info from this intent
    //in our composable.
//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//    }
//
//    //process the deeplink intent
//    fun getPostIdFromDeepLink(intent: Intent?): String? {
//        intent?.let {
//            if (it.action == Intent.ACTION_WIEW && it.data != null) {
//                it.data?.pathSegments?.firstOrNull()?.let { postId ->
//                    return postId
//                }
//            }
//        }
//    }




    private fun shouldShowBottomBar(backStackEntry: NavBackStackEntry?): Boolean{
        val doesRouteMatch = backStackEntry?.destination?.route in listOf(
            Screen.MainFeedScreen.route,
            Screen.ChatScreen.route,
            Screen.ActivityScreen.route,
            //only if the yser id is null we show the bottom bar,
            //because that means we are on our own profile.
            Screen.ProfileScreen.route
        )
        val isOwnProfile = backStackEntry?.destination?.route == "${Screen.ProfileScreen.route}?userId={userId}" &&
                backStackEntry?.arguments?.getString("userId") == null

        return doesRouteMatch || isOwnProfile
    }
}

