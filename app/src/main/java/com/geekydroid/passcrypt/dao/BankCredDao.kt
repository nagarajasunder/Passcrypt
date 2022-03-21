package com.geekydroid.passcrypt.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.geekydroid.passcrypt.entities.BankCred
import com.geekydroid.passcrypt.entities.Card

@Dao
interface BankCredDao {

    @Insert
    suspend fun insertCard(cardCred: Card): Long

    @Insert
    suspend fun insertBankCred(cred: BankCred): Long

    @Query("SELECT * FROM BANK_CRED WHERE credId = :credId")
    suspend fun getBankCredById(credId: Int): BankCred

    @Query("SELECT * FROM BANK_CRED")
    fun getAllBankCreds(): LiveData<List<BankCred>>

    @Transaction
    suspend fun createBankWithCard(bankCred: BankCred, cardCred: Card): Long {
        val bankId = insertBankCred(bankCred)
        if (bankId == -1L) return -1L
        cardCred.bankId = bankId.toInt()
        return insertCard(cardCred);
    }

    @Query("SELECT * FROM CARD WHERE bankId = :bankId")
    suspend fun getCardCredByBank(bankId: Int): List<Card>

    suspend fun getBankCredWithCards(credId: Int): Pair<BankCred, List<Card>>? {
        val bankCred: BankCred = getBankCredById(credId)
        val cardCred = getCardCredByBank(bankCred.credId)
        return Pair(bankCred, cardCred)

    }

    @Update
    suspend fun updateBankCred(bankCred: BankCred)
}