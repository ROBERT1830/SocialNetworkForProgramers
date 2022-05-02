package com.robertconstantindinescu.my_social_network.core.util

import android.content.Context
import androidx.annotation.StringRes
import com.robertconstantindinescu.my_social_network.R

sealed class UiText{
    data class DynamicString(val value: String): UiText()
    data class StringResource(@StringRes val id: Int): UiText()

    companion object{

        fun unknownError(): UiText {
            return UiText.StringResource(R.string.error_unknown)
        }
    }
}
