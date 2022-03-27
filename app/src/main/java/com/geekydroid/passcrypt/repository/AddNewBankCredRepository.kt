package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.dao.BankCredDao
import com.geekydroid.passcrypt.entities.BankCred
import com.geekydroid.passcrypt.entities.Card
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddNewBankCredRepository @Inject constructor(private val bankDao: BankCredDao) {


    suspend fun insertBankCred(bankCred: BankCred, cardCred: Card): Long {
        return bankDao.createBankWithCard(bankCred, cardCred)
    }

    suspend fun insertBank(bankCred: BankCred) {
        bankDao.insertBankCred(bankCred)
    }
}