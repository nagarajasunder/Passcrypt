package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.Utils.SortPreference
import com.geekydroid.passcrypt.dao.AccountCredDao
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class HomeFragmentRepository @Inject constructor(private val accountDao: AccountCredDao) {


    init {
        println("debug: dao home $accountDao")
    }

    fun getAccountCreds(searchText: String, sortPreference: String) =
        accountDao.getCredentialsBasedOnFilters(searchText, sortPreference)


}