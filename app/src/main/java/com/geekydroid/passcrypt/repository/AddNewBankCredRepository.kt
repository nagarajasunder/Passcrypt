package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import com.geekydroid.passcrypt.entities.BankCred
import com.geekydroid.passcrypt.entities.Card
import javax.inject.Inject

class AddNewBankCredRepository @Inject constructor(private val database: EncryptedDataSource) {

    suspend fun insertBankCred(bankCred: BankCred, cardCred: Card): Long {
        return database.getBankCredDao().createBankWithCard(bankCred, cardCred)
    }
}