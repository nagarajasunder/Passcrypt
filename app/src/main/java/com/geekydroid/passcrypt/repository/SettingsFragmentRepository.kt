package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.datasources.LocalDataSource
import javax.inject.Inject


class SettingsFragmentRepository @Inject constructor(private val database: LocalDataSource) {
    suspend fun updateSelfDestruction() {
        database.getUserdao().updateSelfDestruction()
    }
}