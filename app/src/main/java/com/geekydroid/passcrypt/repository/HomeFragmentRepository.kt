package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import javax.inject.Inject


class HomeFragmentRepository @Inject constructor(private val database: EncryptedDataSource) {

    fun getAccountCreds(searchText: String = "") =
        if (searchText.isEmpty()) database.getAccountCredDao().getAllCredentials() else
            database.getAccountCredDao().getAllCredentialsBySearch(searchText)

}