package com.robertconstantindinescu.my_social_network.core.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.robertconstantindinescu.my_social_network.R


@SuppressLint("StringFormatInvalid")
fun Context.sendSharePostIntent(postId: String) {
    //launch an intentn in compose looks like that.
    //We want to share it in instagram, whats or soo so we need
    //to launch an intentn for taht
    val intent = Intent().apply {
        action = Intent.ACTION_SEND //Not be confused with ACTION_VIEW tis is an intent we want to open in or app. and the send one is to send soemething to an other app
        putExtra(
            Intent.EXTRA_TEXT,
            getString(
                R.string.share_intent_text,
                //is needed the url in the extra becuase is what you want to share
                "https://pl-coding.com/$postId"
            )
        )
        type = "text/plain"
    }

    //check if the intent can be resolved. i mean if you have an app rthat
    // could send the information or open rthe information we want will
    //start the intentn with the aplications we have for the give prupose.
    //Here we need queries in the manifest. Otherwise here we will have a warning
    if(intent.resolveActivity(packageManager) != null) {
        startActivity(Intent.createChooser(intent, "Select an app"))
    }
}

fun Context.openUrlInBrowser(url: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    startActivity(Intent.createChooser(intent, "Select an app"))
}