package com.robertconstantindinescu.my_social_network.core.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.robertconstantindinescu.my_social_network.R

@Composable
fun StandardToolBar(
    navController: NavController,
    modifier: Modifier = Modifier,
    showBackArrow: Boolean = false,
    //composable for the icon
    navAction: @Composable RowScope.() -> Unit = {},
    //title is a composable to pass profile image to our chat screen
    title: @Composable () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = {
            if (showBackArrow){
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(
                        id = R.string.navigate_back),
                        tint = MaterialTheme.colors.onBackground
                    )
                }

            }else null
        },
        actions = navAction,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp



    )

}