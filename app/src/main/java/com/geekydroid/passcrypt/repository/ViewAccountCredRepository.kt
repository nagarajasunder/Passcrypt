package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import com.geekydroid.passcrypt.datasources.LocalDataSource
import com.geekydroid.passcrypt.entities.AccountCred

class ViewAccountCredRepository(private val database: EncryptedDataSource) {

    suspend fun getAccountCredById(credId: Int) = database.getAccountCredDao().getCredById(credId)

}