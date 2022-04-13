package com.robertconstantindinescu.my_social_network

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SocialNetworkApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        //Login library with plant we init the debugger try
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }

    }
}