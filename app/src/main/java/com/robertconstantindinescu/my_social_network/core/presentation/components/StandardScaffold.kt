package com.robertconstantindinescu.my_social_network.core.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.domain.models.BottomNavItem
import com.robertconstantindinescu.my_social_network.core.util.Screen

@Composable
fun StandardScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    showBottomBar: Boolean = true,
    state: ScaffoldState,
    showToolbar: Boolean = false,
    showBackArrow: Boolean = true,
    //Lambda that represents a composable. Need to have access to RowScope.
    navActions: @Composable RowScope.() -> Unit = {},
    toolBarTitle: String? = null, //some screens don't have title
    bottomNavItems: List<BottomNavItem> = listOf(
        BottomNavItem(
            route = Screen.MainFeedScreen.route,
            icon = Icons.Outlined.Home,
            contentDescription = "Home"
        ),
        BottomNavItem(
            route = Screen.ChatScreen.route,
            icon = Icons.Outlined.Message,
            contentDescription = "Message"
        ),
        BottomNavItem(
            route = "-"
        ),
        BottomNavItem(
            route = Screen.ActivityScreen.route,
            icon = Icons.Default.Notifications,
            contentDescription = "Activity"
        ),
        BottomNavItem(
            route = Screen.ProfileScreen.route,
            icon = Icons.Outlined.Person,
            contentDescription = "Profile"
        )
    ),
    onFabClick: () -> Unit = {},
    content: @Composable () -> Unit

) {

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showBottomBar) {
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colors.surface,
                    cutoutShape = CircleShape,
                    elevation = 5.dp
                ) {
                    BottomNavigation(modifier = Modifier.fillMaxWidth()) {
                        bottomNavItems.forEachIndexed { index, bottomNavItem ->
                            StandardBottomNavItem(
                                icon = bottomNavItem.icon,
                                contentDescription = bottomNavItem.contentDescription,
                                selected = navController.currentDestination?.route?.startsWith(bottomNavItem.route) == true,
                                alertCount = bottomNavItem.alertCount,
                                //if we have an icon enable te icon else not. And that will be used
                                //to create an other item just to separate the middle ones from the fab
                                enabled = bottomNavItem.icon != null
                            ) {
                                //only navigate if the selected icon is not home.
                                //we don't want to navigate to home if we pres home. or navigate to the
                                //same screen over and over again
                                if (navController.currentDestination?.route != bottomNavItem.route) {
                                    navController.navigate(bottomNavItem.route)
                                }

                            }
                        }

                    }


                }
            }


        },
        scaffoldState = state,
        floatingActionButton = {
            if (showBottomBar) {
                FloatingActionButton(
                    backgroundColor = MaterialTheme.colors.primary,
                    onClick = onFabClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.make_post)
                    )

                }
            }

        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {

        content()
    }

}