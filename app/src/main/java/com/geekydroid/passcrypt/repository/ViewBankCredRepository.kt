package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.dao.BankCredDao
import com.geekydroid.passcrypt.entities.BankCred
import com.geekydroid.passcrypt.entities.Card
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewBankCredRepository @Inject constructor(private val bankDao: BankCredDao) {

    suspend fun getBankCredWithCards(credId: Int): Pair<BankCred, List<Card>>? {
        return bankDao.getBankCredWithCards(credId)
    }

    suspend fun updateBank(bankCred: BankCred) {
        bankDao.updateBankCred(bankCred)
    }

    suspend fun deleteBankCredential(bankCred: BankCred) {

        bankDao.deleteBank(bankCred)

    }


}