package com.geekydroid.passcrypt.datasources

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geekydroid.passcrypt.entities.User
import com.geekydroid.passcrypt.dao.UserDao


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class LocalDataSource : RoomDatabase() {
    abstract fun getUserdao(): UserDao
}