package com.robertconstantindinescu.my_social_network.presentation.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.presentation.util.Screen
import com.robertconstantindinescu.my_social_network.util.ConstVal.SPLASH_SCREEN_DURATION
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen(
    navController: NavController,
    dispatcher: CoroutineDispatcher = Dispatchers.Main
) {

    val scale = remember {
        Animatable(0f)
    }
    //overshoot --> no just animate to our target value will animate a little bit more than that and then
    //go back to the target.
    val overshootInterpolator = remember {
        OvershootInterpolator(2f)
    }

    LaunchedEffect(key1 = true) {
        withContext(dispatcher){
            scale.animateTo(
                targetValue = 0.5f,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = {
                        //fraction of animation tht is allready played.
                        overshootInterpolator.getInterpolation(it)
                    }
                )

            )
            delay(SPLASH_SCREEN_DURATION)
            navController.popBackStack()
            navController.navigate(Screen.LoginScreen.route)
        }

    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.scale(scale.value),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Logo"
        )
    }


}