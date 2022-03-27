package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.dao.BankCredDao
import com.geekydroid.passcrypt.entities.BankCred
import com.geekydroid.passcrypt.entities.Card
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EditBankCredRepository @Inject constructor(private val bankDao: BankCredDao) {


    suspend fun getBankCred(credId: Int) = bankDao.getBankCredById(credId)

    suspend fun getCardCredByBankAccountId(bankAccountId: Int) =
        bankDao.getCardCredByBank(bankAccountId)

    suspend fun updateBank(bankCred: BankCred) {
        bankDao.updateBankCred(bankCred)
    }

    suspend fun insertNewCard(card: Card) {
        bankDao.insertCard(card)
    }

    suspend fun updateCard(card: Card) {
        bankDao.updateCard(card)
    }


}