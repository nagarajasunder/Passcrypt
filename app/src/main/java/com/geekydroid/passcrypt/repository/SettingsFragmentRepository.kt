package com.geekydroid.passcrypt.repository

import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import com.geekydroid.passcrypt.datasources.LocalDataSource
import com.geekydroid.passcrypt.entities.User
import javax.inject.Inject


class SettingsFragmentRepository @Inject constructor(
    private val database: LocalDataSource,
    private val encryptedDataSource: EncryptedDataSource
) {


    fun getUserSettings() = database.getUserdao().getUser()
    suspend fun updateUser(user: User) {
        database.getUserdao().updateUser(user)
    }

    suspend fun getCompleteAccountData() =
        encryptedDataSource.getAccountCredDao().getAllAccountData()

    suspend fun getCompleteBankData() = encryptedDataSource.getBankCredDao().getAllBankData()


}