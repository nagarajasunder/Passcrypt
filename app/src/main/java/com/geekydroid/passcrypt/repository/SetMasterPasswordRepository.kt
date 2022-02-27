package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.datasources.LocalDataSource
import com.geekydroid.passcrypt.entities.User
import javax.inject.Inject

class SetMasterPasswordRepository @Inject constructor(private val database: LocalDataSource) {

    suspend fun insertUser(user: User): Long {
        return database.getUserdao().insertUser(user)
    }
}