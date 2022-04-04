package com.geekydroid.passcrypt.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.geekydroid.passcrypt.Utils.SortPreference
import com.geekydroid.passcrypt.entities.AccountCred
import com.geekydroid.passcrypt.entities.CredWrapper

@Dao
interface AccountCredDao {

    @Transaction
    suspend fun deleteDatabase() {
        truncateAccounts()
        truncateBanks()
    }

    @Update
    suspend fun updateCred(accountCred: AccountCred)

    @Query("DELETE FROM ACCOUNT_CRED")
    suspend fun truncateAccounts()

    @Query("DELETE FROM BANK_CRED")
    suspend fun truncateBanks()

    @Insert
    suspend fun insertCred(cred: AccountCred)

    @Query(
        "SELECT * FROM ACCOUNT_CRED WHERE SITE_NAME LIKE '%' || :searchText || '%' " +
                "OR USER_NAME LIKE '%' || :searchText || '%' " +
                "OR comments LIKE '%' || :searchText || '%'"
    )
    fun getAllCredentialsBySearch(searchText: String): LiveData<List<AccountCred>>

    @Query("SELECT * FROM ACCOUNT_CRED")
    fun getAllCredentials(): LiveData<List<AccountCred>>

    @Query("SELECT * FROM ACCOUNT_CRED WHERE CRED_ID = :credId")
    fun getCredById(credId: Int): LiveData<AccountCred>

    @Query(
        "SELECT * FROM (SELECT BANK_CRED_ID as credId  , " +
                "BANK_NAME as credTitle , " +
                "COMMENTS as credComments , " +
                "'BANK' as credType , " +
                "CREATED_ON as createdOn,  " +
                "IS_FAVORITE as isFavorite " +
                "FROM BANK_CRED " +
                "UNION ALL SELECT CRED_ID as credId  , " +
                "TITLE as credTitle , " +
                "COMMENTS as credComments , " +
                "'ACCOUNT' as credType , " +
                "CREATED_ON as createdOn, " +
                "IS_FAVORITE as isFavorite " +
                "FROM ACCOUNT_CRED) as data " +
                "WHERE credTitle LIKE '%' || :searchText || '%' OR credComments LIKE '%' || :searchText || '%' ORDER BY isFavorite DESC ,credTitle ASC"
    )
    fun getCredSortedByNameAsc(searchText: String): LiveData<List<CredWrapper>>

    @Query(
        "SELECT * FROM (SELECT BANK_CRED_ID as credId  , " +
                "BANK_NAME as credTitle , " +
                "COMMENTS as credComments , " +
                "'BANK' as credType , " +
                "CREATED_ON as createdOn,  " +
                "IS_FAVORITE as isFavorite " +
                "FROM BANK_CRED " +
                "UNION ALL SELECT CRED_ID as credId  , " +
                "TITLE as credTitle , " +
                "COMMENTS as credComments , " +
                "'ACCOUNT' as credType , " +
                "CREATED_ON as createdOn, " +
                "IS_FAVORITE as isFavorite " +
                "FROM ACCOUNT_CRED) as data " +
                "WHERE credTitle LIKE '%' || :searchText || '%' OR credComments LIKE '%' || :searchText || '%' ORDER BY isFavorite DESC ,credTitle DESC"
    )
    fun getCredSortedByNameDesc(searchText: String): LiveData<List<CredWrapper>>

    @Query(
        "SELECT * FROM (SELECT BANK_CRED_ID as credId  , " +
                "BANK_NAME as credTitle , " +
                "COMMENTS as credComments , " +
                "'BANK' as credType , " +
                "CREATED_ON as createdOn,  " +
                "IS_FAVORITE as isFavorite " +
                "FROM BANK_CRED " +
                "UNION ALL SELECT CRED_ID as credId  , " +
                "TITLE as credTitle , " +
                "COMMENTS as credComments , " +
                "'ACCOUNT' as credType , " +
                "CREATED_ON as createdOn, " +
                "IS_FAVORITE as isFavorite " +
                "FROM ACCOUNT_CRED) as data " +
                "WHERE credTitle LIKE '%' || :searchText || '%' OR credComments LIKE '%' || :searchText || '%' ORDER BY isFavorite DESC ,createdOn ASC"
    )
    fun getCredSortedByDateAsc(searchText: String): LiveData<List<CredWrapper>>

    @Query(
        "SELECT * FROM (SELECT BANK_CRED_ID as credId  , " +
                "BANK_NAME as credTitle , " +
                "COMMENTS as credComments , " +
                "'BANK' as credType , " +
                "CREATED_ON as createdOn,  " +
                "IS_FAVORITE as isFavorite " +
                "FROM BANK_CRED " +
                "UNION ALL SELECT CRED_ID as credId  , " +
                "TITLE as credTitle , " +
                "COMMENTS as credComments , " +
                "'ACCOUNT' as credType , " +
                "CREATED_ON as createdOn, " +
                "IS_FAVORITE as isFavorite " +
                "FROM ACCOUNT_CRED) as data " +
                "WHERE credTitle LIKE '%' || :searchText || '%' OR credComments LIKE '%' || :searchText || '%' ORDER BY isFavorite DESC ,createdOn DESC"
    )
    fun getCredSortedByDateDesc(searchText: String): LiveData<List<CredWrapper>>

    fun getCredentialsBasedOnFilters(
        searchText: String,
        sortPreference: String
    ): LiveData<List<CredWrapper>> {
        return when (sortPreference) {
            SortPreference.SORT_BY_DATE_ASC.name -> getCredSortedByDateAsc(searchText)
            SortPreference.SORT_BY_DATE_DESC.name -> getCredSortedByDateDesc(searchText)
            SortPreference.SORT_BY_NAME_ASC.name -> getCredSortedByNameAsc(searchText)
            else -> getCredSortedByNameDesc(searchText)
        }
    }

    @Delete
    suspend fun deleteCredential(accountCred: AccountCred)
}