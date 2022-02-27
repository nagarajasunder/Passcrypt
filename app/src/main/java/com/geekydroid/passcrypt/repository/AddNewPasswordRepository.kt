package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import com.geekydroid.passcrypt.entities.Credentials
import javax.inject.Inject

class AddNewPasswordRepository @Inject constructor(private val database: EncryptedDataSource) {


    suspend fun insertCred(credentials: Credentials) {
        database.getCredDao().insertCred(credentials)
    }
}