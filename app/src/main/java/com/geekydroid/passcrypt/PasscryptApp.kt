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
    private val ENCRYPTED_DATA_SOURCE = "Passcrypt_encrypt.db"
    private val LOCAL_DATA_SOURCE = "Passcrypt.db"
    private lateinit var MASTER_PASS: String

    fun setMasterPass(passPhrase: String) {
        MASTER_PASS = passPhrase
    }

    fun clearMasterPass() {
        MASTER_PASS = ""
    }

    fun getMasterPass() = MASTER_PASS

    fun getEncryptedDSName() = ENCRYPTED_DATA_SOURCE

    fun getLocalDSName() = LOCAL_DATA_SOURCE

    private val SORT_BY_NAME_ASC = 1
    private val SORT_BY_NAME_DESC = 2
    private val OLDEST_TO_NEWEST = 3
    private val NEWEST_TO_OLDEST = 4

    fun sortByNameAsc() = SORT_BY_NAME_ASC
    fun sortByNameDesc() = SORT_BY_NAME_DESC
    fun oldestToNewest() = OLDEST_TO_NEWEST
    fun newestToOldest() = NEWEST_TO_OLDEST
}
