package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import com.geekydroid.passcrypt.entities.AccountCred
import javax.inject.Inject

class AddNewPasswordRepository @Inject constructor(private val database: EncryptedDataSource) {


    suspend fun insertCred(accountCred: AccountCred) {
        database.getAccountCredDao().insertCred(accountCred)
    }
}