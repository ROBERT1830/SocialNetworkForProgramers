package com.robertconstantindinescu.my_social_network.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.util.TestTags
import androidx.compose.ui.text.TextStyle
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.IconSizeMedium

@Composable
fun StandardTextField(
    text: String = "",
    hint: String = "",
    maxLength: Int = 400,
    error: String = "",
    style: TextStyle = TextStyle(
        color = MaterialTheme.colors.onBackground
    ),
    backgroundColor: Color = MaterialTheme.colors.surface,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    leadingIcon: ImageVector? = null,
    keyBoardType: KeyboardType = KeyboardType.Text,
    isPasswordToggleDisplayed: Boolean = keyBoardType == KeyboardType.Password,
    onValueChange: (String) -> Unit,
    showPasswordToggle: Boolean = false,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier

) {

    //PASS IT THORUGH PARAMETERS BECAUSE WE DONT HAVE CONTROL FROM OUTISE
//    val isPasswordToggleDisplayed by remember {
//        mutableStateOf(keyBoardType == KeyboardType.Password)
//    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)//attatch the modifier we pass.
    ) {
        //semantics : to find latter on testing.
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    testTag = TestTags.STANDARD_TEXT_FIELD
                },
            value = text,
            onValueChange = {
                if (it.length <= maxLength) {
                    onValueChange(it)
                }

            },
            maxLines = maxLines,
            textStyle = style,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = backgroundColor
            ),
            placeholder = {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.body1
                )
            },
            isError = error != "",
            keyboardOptions = KeyboardOptions(
                keyboardType = keyBoardType,
            ),
            visualTransformation = if (showPasswordToggle && isPasswordToggleDisplayed) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            singleLine = singleLine,
            leadingIcon = if (leadingIcon != null) {
                {
                    Icon(
                        imageVector = leadingIcon, contentDescription = null,
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium)
                    )
                }
            } else null,
            trailingIcon = if (isPasswordToggleDisplayed) {
                {
                    IconButton(onClick = {
                        onPasswordToggleClick(!showPasswordToggle)
                    },
                        modifier = Modifier
                            .semantics { testTag = TestTags.PASSWORD_TOGGLE }
                    ) {
                        Icon(
                            imageVector = if (showPasswordToggle) {
                                Icons.Filled.VisibilityOff
                            } else {
                                Icons.Filled.Visibility

                            }, contentDescription = if (showPasswordToggle) {
                                stringResource(R.string.password_visible_content_description)
                            } else {
                                stringResource(R.string.password_hidden_content_description)
                            },
                            tint = Color.White
                        )
                    }

                }


            }else null

        )
        if (error.isNotEmpty()) {
            Text(
                text = error,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.error,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }

    }


}