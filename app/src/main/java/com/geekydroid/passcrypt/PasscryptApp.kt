package com.geekydroid.passcrypt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Singleton

@HiltAndroidApp
@Singleton
class PasscryptApp : Application() {
    val FIRST_LAUNCH = "FIRST_LAUNCH"

    val NAVIGATION_MODE_RESET = "PASSWORD_RESET"
    val NAVIGATION_MODE_NORMAL = "NORMAL"


    private lateinit var MASTER_PASS: String

    fun setMasterPass(passPhrase: String) {
        MASTER_PASS = passPhrase
    }

    fun clearMasterPass() {
        MASTER_PASS = ""
    }

    fun getMasterPass() = MASTER_PASS
}
