package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.datasources.EncryptedDataSource

class ViewBankCredRepository(private val database: EncryptedDataSource) {

    suspend fun getBankCredWithCards(credId: Int) =
        database.getBankCredDao().getBankCredWithCards(credId)


}