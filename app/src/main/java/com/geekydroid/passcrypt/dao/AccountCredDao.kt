package com.geekydroid.passcrypt.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.geekydroid.passcrypt.entities.AccountCred

@Dao
interface AccountCredDao {

    @Insert
    suspend fun insertCred(cred: AccountCred)

    @Query("SELECT * FROM ACCOUNT_CRED WHERE siteName LIKE '%' || :searchText || '%' " +
            "OR userName LIKE '%' || :searchText || '%' " +
            "OR comments LIKE '%' || :searchText || '%'")
    fun getAllCreds(searchText: String): LiveData<List<AccountCred>>

    @Query("SELECT * FROM ACCOUNT_CRED WHERE credId = :credId")
    fun getCredById(credId: Int): AccountCred

}