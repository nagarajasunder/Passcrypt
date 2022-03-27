package com.geekydroid.passcrypt.di

import android.app.Application
import androidx.room.Room
import com.geekydroid.passcrypt.dao.AccountCredDao
import com.geekydroid.passcrypt.dao.BankCredDao
import com.geekydroid.passcrypt.dao.UserDao
import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import com.geekydroid.passcrypt.datasources.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesDatabase(app: Application): LocalDataSource {
        return Room.databaseBuilder(
            app,
            LocalDataSource::class.java,
            "Passcrypt.db"
        ).build()
    }


    @Provides
    @Singleton
    fun providesEncryptedDataSource(app: Application): EncryptedDataSource {
        return Room.databaseBuilder(
            app,
            EncryptedDataSource::class.java,
            "Passcrypt-encrypt.db"
        ).build()
    }

    @Provides
    fun providesDao(database: LocalDataSource): UserDao {
        return database.getUserdao()
    }

    @Provides
    fun providesAccountCredDao(database: EncryptedDataSource): AccountCredDao {
        return database.getAccountCredDao()
    }

    @Provides
    fun providesBankCredDao(database: EncryptedDataSource): BankCredDao {
        return database.getBankCredDao()
    }
}
