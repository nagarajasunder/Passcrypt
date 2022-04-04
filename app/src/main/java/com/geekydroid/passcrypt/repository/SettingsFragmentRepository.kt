package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.datasources.LocalDataSource
import javax.inject.Inject


class SettingsFragmentRepository @Inject constructor(private val database: LocalDataSource) {
    suspend fun updateSelfDestruction(switch: Boolean) {
        database.getUserdao().updateSelfDestruction(switch)
    }

    fun getUserSettings() = database.getUserdao().getUser()

}