package com.geekydroid.passcrypt.datasources

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geekydroid.passcrypt.dao.CredDao
import com.geekydroid.passcrypt.entities.Credentials

@Database(entities = [Credentials::class], version = 1, exportSchema = false)
abstract class EncryptedDataSource : RoomDatabase() {

    abstract fun getCredDao(): CredDao
}