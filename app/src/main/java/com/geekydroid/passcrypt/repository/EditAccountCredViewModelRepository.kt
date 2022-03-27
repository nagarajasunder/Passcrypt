package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.dao.AccountCredDao
import com.geekydroid.passcrypt.entities.AccountCred
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditAccountCredViewModelRepository @Inject constructor(private val accountDao:AccountCredDao) {

    suspend fun updateAccountCred(cred: AccountCred) {
        accountDao.updateCred(cred)
    }

    fun getCredByCredId(credId: Int) = accountDao.getCredById(credId)
}