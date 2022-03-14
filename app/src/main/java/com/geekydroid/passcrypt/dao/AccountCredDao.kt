package com.geekydroid.passcrypt.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.geekydroid.passcrypt.entities.AccountCred
import com.geekydroid.passcrypt.entities.CredWrapper

@Dao
interface AccountCredDao {

    @Transaction
    suspend fun deleteDatabase() {
        truncateAccounts()
        truncateBanks()
    }

    @Query("DELETE FROM ACCOUNT_CRED")
    suspend fun truncateAccounts()

    @Query("DELETE FROM BANK_CRED")
    suspend fun truncateBanks()

    @Insert
    suspend fun insertCred(cred: AccountCred)

    @Query(
        "SELECT * FROM ACCOUNT_CRED WHERE siteName LIKE '%' || :searchText || '%' " +
                "OR userName LIKE '%' || :searchText || '%' " +
                "OR comments LIKE '%' || :searchText || '%'"
    )
    fun getAllCredentialsBySearch(searchText: String): LiveData<List<AccountCred>>

    @Query("SELECT * FROM ACCOUNT_CRED")
    fun getAllCredentials(): LiveData<List<AccountCred>>

    @Query("SELECT * FROM ACCOUNT_CRED WHERE credId = :credId")
    suspend fun getCredById(credId: Int): AccountCred

    @Query(
        "SELECT credId as credId  , " +
                "siteName as credName , " +
                "comments as credComments , " +
                "'ACCOUNT' as credType , " +
                "createdOn as createdOn  " +
                "FROM ACCOUNT_CRED " +
                "UNION ALL SELECT credId as credId  , " +
                "bankName as credName , " +
                "comments as credComments , " +
                "'BANK' as credType , " +
                "createdOn as createdOn FROM BANK_CRED ORDER BY createdOn"
    )
    fun getCombinedCreds(): LiveData<List<CredWrapper>>
}