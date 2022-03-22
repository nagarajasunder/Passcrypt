package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import com.geekydroid.passcrypt.entities.BankCred
import com.geekydroid.passcrypt.entities.Card
import javax.inject.Inject


class EditBankCredRepository @Inject constructor(private val database: EncryptedDataSource) {

    suspend fun getBankCred(credId: Int) = database.getBankCredDao().getBankCredById(credId)

    suspend fun getCardCredByBankAccountId(bankAccountId: Int) =
        database.getBankCredDao().getCardCredByBank(bankAccountId)

    suspend fun updateBank(bankCred: BankCred) {
        database.getBankCredDao().updateBankCred(bankCred)
    }

    suspend fun insertNewCard(card: Card) {
        database.getBankCredDao().insertCard(card)
    }

    suspend fun updateCard(card: Card) {
        database.getBankCredDao().updateCard(card)
    }


}