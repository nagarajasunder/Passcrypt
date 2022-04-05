package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.datasources.LocalDataSource
import com.geekydroid.passcrypt.entities.User
import javax.inject.Inject


class SettingsFragmentRepository @Inject constructor(private val database: LocalDataSource) {


    fun getUserSettings() = database.getUserdao().getUser()
    suspend fun updateUser(user: User) {
        database.getUserdao().updateUser(user)
    }

}