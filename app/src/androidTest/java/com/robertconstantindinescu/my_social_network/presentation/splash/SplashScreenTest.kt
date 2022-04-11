package com.robertconstantindinescu.my_social_network.presentation.splash

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.runner.AndroidJUnitRunner
import com.robertconstantindinescu.my_social_network.presentation.MainActivity
import com.robertconstantindinescu.my_social_network.presentation.ui.theme.My_social_networkTheme
import com.robertconstantindinescu.my_social_network.presentation.util.Screen
import com.robertconstantindinescu.my_social_network.util.ConstVal
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.internal.assertThreadDoesntHoldLock
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 *
 */

@RunWith(AndroidJUnit4::class)
class SplashScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /*Mock : is an object that doesn't have the real behaviour
    * in the test cases we don't have access to navController.
    * The mock tracks how ofter each function of the test is called
    * so we can check if we wait for the delay or if the popupBackStack is called
    *  */

    @RelaxedMockK
    lateinit var navController: NavController

    private val testDispatcher =  TestCoroutineDispatcher()

    /*This function is called by JUNIT before every single test case. Is used
    * to initialize objects or mocks. */
    @Before
    fun setUp(){
        //this init the mocck
        MockKAnnotations.init(this)
    }

    //use runBlocking because we are testing a coroutine.
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun splashScreen_displaysAndDisappears() = testDispatcher.runBlockingTest{
        composeTestRule.setContent {
            My_social_networkTheme {
                SplashScreen(navController = navController, dispatcher = testDispatcher)
            }
        }
        //node is a ui element in the screen and now we have to find the image
        //Check if there is an image source with a content description "Logo"
        composeTestRule
            .onNodeWithContentDescription("Logo")
            .assertExists()

        advanceTimeBy(ConstVal.SPLASH_SCREEN_DURATION)

        //verify that the popBackStack and navigation functions are called
        /**
         * The test failed because the LaunchedEffect block launches a different coroutine
         * in a different scope and it doesn't use the scope ot he testCoroutineScope. And
         * the scope that LaunchedEffect uses might not cancel the delay by the runBlockingTest
         *
         * Sum up: this coroutine block do not skip the delay.
         * test if both functions are called.
         */
        //advanceTimeBy(ConstVal.SPLASH_SCREEN_DURATION)
        verify {
            navController.popBackStack()
            navController.navigate(Screen.LoginScreen.route)

        }

    }

}





