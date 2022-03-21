package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import com.geekydroid.passcrypt.entities.AccountCred
import javax.inject.Inject

class EditAccountCredViewModelRepository @Inject constructor(private val database: EncryptedDataSource) {

    suspend fun updateAccountCred(cred:AccountCred)
    {
        database.getAccountCredDao().updateCred(cred)
    }

     fun getCredByCredId(credId:Int) = database.getAccountCredDao().getCredById(credId)
}