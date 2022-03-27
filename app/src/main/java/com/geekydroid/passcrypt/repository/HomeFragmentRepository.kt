package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.dao.AccountCredDao
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class HomeFragmentRepository @Inject constructor(private val accountDao: AccountCredDao) {


    init {
        println("debug: dao home $accountDao")
    }


    fun getAccountCreds(searchText: String = "") = accountDao.getCombinedCreds(searchText)


}