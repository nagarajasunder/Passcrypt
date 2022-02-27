package com.geekydroid.passcrypt.di

import android.content.Context
import androidx.room.Room
import com.geekydroid.passcrypt.dao.UserDao
import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import com.geekydroid.passcrypt.datasources.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providesDao(database: LocalDataSource): UserDao {
        return database.getUserdao()
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): LocalDataSource {
        return Room.databaseBuilder(
            context,
            LocalDataSource::class.java,
            "Passcrypt.db"
        ).build()
    }


    @Singleton
    @Provides
    fun providesEncryptedDataSource(@ApplicationContext context: Context): EncryptedDataSource {
        return Room.databaseBuilder(
            context,
            EncryptedDataSource::class.java,
            "Passcrypt-encrypt.db"
        ).build()
    }


}