package com.geekydroid.passcrypt.di

import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import com.geekydroid.passcrypt.datasources.LocalDataSource
import com.geekydroid.passcrypt.repository.AddNewPasswordRepository
import com.geekydroid.passcrypt.repository.EnterMasterPasswordRepository
import com.geekydroid.passcrypt.repository.SetMasterPasswordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesSetMasterPasswordRepo(database: LocalDataSource): SetMasterPasswordRepository {
        return SetMasterPasswordRepository(database)
    }

    @Provides
    @Singleton
    fun providesEnterMasterPasswordRepo(database: LocalDataSource): EnterMasterPasswordRepository {
        return EnterMasterPasswordRepository(database)
    }

    @Provides
    @Singleton
    fun providesAddNewPasswordRepo(database: EncryptedDataSource): AddNewPasswordRepository {
        return AddNewPasswordRepository(database)
    }


}