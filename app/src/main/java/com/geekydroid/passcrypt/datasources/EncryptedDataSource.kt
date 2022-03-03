package com.geekydroid.passcrypt.datasources

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geekydroid.passcrypt.dao.AccountCredDao
import com.geekydroid.passcrypt.dao.BankCredDao
import com.geekydroid.passcrypt.entities.AccountCred
import com.geekydroid.passcrypt.entities.BankCred

@Database(entities = [AccountCred::class, BankCred::class], version = 1, exportSchema = false)
abstract class EncryptedDataSource : RoomDatabase() {

    abstract fun getAccountCredDao(): AccountCredDao

    abstract fun getBankCredDao(): BankCredDao
}