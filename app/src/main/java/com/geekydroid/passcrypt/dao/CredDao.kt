package com.geekydroid.passcrypt.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.geekydroid.passcrypt.entities.Credentials

@Dao
interface CredDao {

    @Insert
    suspend fun insertCred(cred: Credentials)

    @Query("SELECT * FROM CREDENTIALS")
    fun getAllCreds(): LiveData<List<Credentials>>

    @Query("SELECT * FROM CREDENTIALS WHERE credId = :credId")
    fun getCredById(credId: Int): Credentials

}