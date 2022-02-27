package com.geekydroid.passcrypt.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.geekydroid.passcrypt.entities.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User):Long

    @Query("SELECT * FROM USER")
    suspend fun getUser(): User?

    @Query("SELECT isMasterPassSet FROM USER")
    fun isMasterPasswordSet(): Boolean
}