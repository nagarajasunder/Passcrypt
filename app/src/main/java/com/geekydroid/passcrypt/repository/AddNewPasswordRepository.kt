package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.dao.AccountCredDao
import com.geekydroid.passcrypt.entities.AccountCred
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddNewPasswordRepository @Inject constructor(private val accountDao: AccountCredDao) {

    init {
        println("debug: dao add $accountDao")
    }


    suspend fun insertCred(accountCred: AccountCred) {
        accountDao.insertCred(accountCred)
    }
}