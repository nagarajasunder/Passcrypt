package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.dao.AccountCredDao
import com.geekydroid.passcrypt.entities.AccountCred
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ViewAccountCredRepository @Inject constructor(private val accountDao: AccountCredDao) {


    init {
        println("debug: dao view $accountDao")
    }

    fun getAccountCredById(credId: Int) = accountDao.getCredById(credId)
    suspend fun addToFavorites(accountCred: AccountCred) {
        accountDao.updateCred(accountCred)
    }

    suspend fun deleteAccountCredential(accountCred: AccountCred) {
        accountDao.deleteCredentail(accountCred)
    }

}