package com.geekydroid.passcrypt.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.geekydroid.passcrypt.entities.BankCred

@Dao
interface BankCredDao {

    @Insert
    suspend fun insertBankCred(cred: BankCred)

    @Query("SELECT * FROM BANK_CRED WHERE credId = :credId")
    suspend fun getBankCredById(credId: Int):BankCred

    @Query("SELECT * FROM BANK_CRED")
    fun getAllBankCreds():LiveData<List<BankCred>>
}