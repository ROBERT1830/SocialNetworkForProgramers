package com.robertconstantindinescu.my_social_network.presentation.components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.KeyboardType
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.robertconstantindinescu.my_social_network.presentation.MainActivity
import com.robertconstantindinescu.my_social_network.presentation.login.LoginScreen
import com.robertconstantindinescu.my_social_network.presentation.util.TestTags.STANDARD_TEXT_FIELD
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.exp

@RunWith(AndroidJUnit4::class)
class StandardTextFieldTest{

    //test maximum text size
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    //Test the text field length
    @Test
    fun enterTooLongString_maxLengthNotExceeded(){
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
                    maxLength = 5,
                    modifier = Modifier.semantics {
                        testTag = STANDARD_TEXT_FIELD
                    }

                )
            }
        }

        val expectingString = "aaaaa"
        composeTestRule.onNodeWithTag(STANDARD_TEXT_FIELD).performTextClearance()
        //detect the textField to test on
        composeTestRule.onNodeWithTag(STANDARD_TEXT_FIELD)
                //set a test text with more then 5
            .performTextInput(expectingString)
        //try to enter one more a. So this should not trigger the onValueChange function and we should
        //still hve 5 a and not 6
        composeTestRule.onNodeWithTag(STANDARD_TEXT_FIELD)
            .performTextInput("a")

         /*Because the onValueChange does not trigger when entering the 6th "a", then we will end
         * up with 5 "a". So we assert that the text field will have 5 "a" even if the user
         * try to write an other one. */
        composeTestRule
            .onNodeWithTag(STANDARD_TEXT_FIELD)
            .assertTextEquals(expectingString)

    }

    @Test
    fun enter_password_toggle_visibility_password(){
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
                    keyBoardType = KeyboardType.Password,
                    //set it to 5 for testing. Not need to be the same as the original text field
                    maxLength = 5,
                    modifier = Modifier.semantics {
                        testTag = STANDARD_TEXT_FIELD
                    }

                )
            }
        }

        //FIND STANDARD TEXT
        composeTestRule.onNodeWithTag(STANDARD_TEXT_FIELD)
            .performTextInput("aaaaa")


    }


}


























