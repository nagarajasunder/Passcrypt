package com.geekydroid.passcrypt.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.geekydroid.passcrypt.entities.User

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM USER")
    fun getUser(): User

    @Query("SELECT isMasterPassSet FROM USER")
    fun isMasterPasswordSet(): Boolean
}