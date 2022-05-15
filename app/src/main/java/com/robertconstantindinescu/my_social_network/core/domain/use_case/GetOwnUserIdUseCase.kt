package com.robertconstantindinescu.my_social_network.core.domain.use_case

import android.content.SharedPreferences
import androidx.compose.ui.unit.Constraints
import com.robertconstantindinescu.my_social_network.core.util.Constants

class GetOwnUserIdUseCase(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(): String{
        return sharedPreferences.getString(Constants.KEY_USER_ID, "") ?:""
    }
}