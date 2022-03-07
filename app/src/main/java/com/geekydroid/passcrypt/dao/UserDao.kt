package com.geekydroid.passcrypt.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.geekydroid.passcrypt.entities.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM USER")
    fun getUser(): LiveData<User>

    @Query("SELECT * FROM USER")
    suspend fun getUserForAuth(): User?

    @Query("SELECT isMasterPassSet FROM USER")
    fun isMasterPasswordSet(): Boolean

    @Query("UPDATE USER set selfDestructive = 1 , selfDestructiveCount = 3 WHERE userId = 1")
    suspend fun updateSelfDestruction()

    @Update
    suspend fun updateUser(user: User)

    @Transaction
    suspend fun resetUser(passwordHash: String) {
        val user = getUserForAuth()
        user?.let {
            user.selfDestructiveCount = 0
            user.selfDestructive = false
            updateUser(user)
        }
    }
}