package com.robertconstantindinescu.my_social_network.presentation.components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.robertconstantindinescu.my_social_network.presentation.MainActivity
import com.robertconstantindinescu.my_social_network.presentation.login.LoginScreen
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StandardTextFieldTest{

    //test maximum text size
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp(){
        composeTestRule.setContent {
            var text by remember {
                mutableStateOf("")
            }
            MaterialTheme{
                StandardTextField(
                    text = text,
                    onValueChange = {
                        text = it
                    },
                    //set it to 5 for testing. Not need to be the same as the original text field
                    maxLength = 5
                )
            }
        }
    }

    @Test
    fun enterTooLongString_maxLengthNotExceeded(){
        //detect the textField to test on
        composeTestRule.onNodeWithTag("standard_text_field")
                //set a test text with more then 6
            .performTextInput("123456")

        composeTestRule.onNodeWithTag("standard_text_field")
            .assertTextEquals("123456")

    }
}