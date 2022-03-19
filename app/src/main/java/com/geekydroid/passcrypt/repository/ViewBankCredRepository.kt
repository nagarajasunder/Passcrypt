package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import com.geekydroid.passcrypt.entities.BankCred
import com.geekydroid.passcrypt.entities.Card

class ViewBankCredRepository(private val database: EncryptedDataSource) {

    suspend fun getBankCredWithCards(credId: Int): Pair<BankCred, List<Card>>? {
        return database.getBankCredDao().getBankCredWithCards(credId)
    }


}