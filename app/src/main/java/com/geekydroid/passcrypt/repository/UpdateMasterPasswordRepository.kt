package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.datasources.LocalDataSource
import com.geekydroid.passcrypt.entities.User
import javax.inject.Inject

class UpdateMasterPasswordRepository @Inject constructor(private val database: LocalDataSource) {

    suspend fun updateUserProfile(user: User) {
        database.getUserdao().updateUser(user)
    }

    fun getUserProfile() = database.getUserdao().getUser()
}