package com.geekydroid.passcrypt

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Singleton

@HiltAndroidApp
@Singleton
class PasscryptApp : Application() {
    val FIRST_LAUNCH = "FIRST_LAUNCH"
    val NAVIGATION_MODE_RESET = "PASSWORD_RESET"
    val NAVIGATION_MODE_NORMAL = "NORMAL"

    val sortByNameAsc: DataStore<Preferences> by preferencesDataStore(name = "SORT_BY_NAME_ASC")
    val Context.sortByNameDesc: DataStore<Preferences> by preferencesDataStore(name = "SORT_BY_NAME_DESC")

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
}
