package com.geekydroid.passcrypt.di

import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import com.geekydroid.passcrypt.datasources.LocalDataSource
import com.geekydroid.passcrypt.repository.*
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

    @Provides
    @Singleton
    fun providesAddNewBankCredRepo(database: EncryptedDataSource): AddNewBankCredRepository {
        return AddNewBankCredRepository(database)
    }

    @Provides
    @Singleton
    fun provideHomeFragmentRepo(database: EncryptedDataSource): HomeFragmentRepository {
        return HomeFragmentRepository(database)
    }

    @Provides
    @Singleton
    fun providesSettingsFragmentRepo(database: LocalDataSource): SettingsFragmentRepository {
        return SettingsFragmentRepository(database)
    }

    @Provides
    @Singleton
    fun providesViewAccountCredRepo(database: EncryptedDataSource): ViewAccountCredRepository {
        return ViewAccountCredRepository(database)
    }

    @Provides
    @Singleton
    fun providesViewBankCredRepo(database: EncryptedDataSource): ViewBankCredRepository {
        return ViewBankCredRepository(database)
    }


}