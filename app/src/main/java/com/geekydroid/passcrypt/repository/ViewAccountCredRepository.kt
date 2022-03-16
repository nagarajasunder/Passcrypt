package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import com.geekydroid.passcrypt.entities.AccountCred

class ViewAccountCredRepository(private val database: EncryptedDataSource) {

    fun getAccountCredById(credId: Int) = database.getAccountCredDao().getCredById(credId)
    suspend fun addToFavorites(accountCred: AccountCred) {
        database.getAccountCredDao().updateCred(accountCred)
    }

}