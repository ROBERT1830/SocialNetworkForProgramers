package com.robertconstantindinescu.my_social_network.core.util

import android.content.Context
import android.view.inputmethod.InputMethodManager

fun Context.showKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    //in compose we don't have views
    imm?.showSoftInput(null, InputMethodManager.SHOW_FORCED)
}