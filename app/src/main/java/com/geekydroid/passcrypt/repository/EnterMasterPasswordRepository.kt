package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.datasources.LocalDataSource
import com.geekydroid.passcrypt.entities.User
import javax.inject.Inject

class EnterMasterPasswordRepository @Inject constructor(private val dataSource: LocalDataSource) {


     fun getUser() = dataSource.getUserdao().getUser()
    suspend fun updateUser(user: User) {
        dataSource.getUserdao().updateUser(user)
    }

}