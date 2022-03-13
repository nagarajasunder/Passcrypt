package com.geekydroid.passcrypt

import android.app.Application
import androidx.multidex.MultiDexApplication
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PasscryptApp : Application()
{
    val FIRST_LAUNCH = "FIRST_LAUNCH"

    val NAVIGATION_MODE_RESET = "PASSWORD_RESET"
    val NAVIGATION_MODE_NORMAL = "NORMAL"
}
