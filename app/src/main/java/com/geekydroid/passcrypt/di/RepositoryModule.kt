package com.geekydroid.passcrypt.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

//    @Provides
//    @Singleton
//    fun providesSetMasterPasswordRepo(database: LocalDataSource): SetMasterPasswordRepository {
//        return SetMasterPasswordRepository(database)
//    }
//
//    @Provides
//    @Singleton
//    fun providesEnterMasterPasswordRepo(database: LocalDataSource): EnterMasterPasswordRepository {
//        return EnterMasterPasswordRepository(database)
//    }
//
//    @Provides
//    @Singleton
//    fun providesAddNewPasswordRepo(accountDao: AccountCredDao): AddNewPasswordRepository {
//        return AddNewPasswordRepository(accountDao)
//    }
//
//    @Provides
//    @Singleton
//    fun providesAddNewBankCredRepo(bankDao: BankCredDao): AddNewBankCredRepository {
//        return AddNewBankCredRepository(bankDao)
//    }
//
//    @Provides
//    @Singleton
//    fun provideHomeFragmentRepo(accountCredDao: AccountCredDao): HomeFragmentRepository {
//        return HomeFragmentRepository(accountCredDao)
//    }
//
//    @Provides
//    @Singleton
//    fun providesSettingsFragmentRepo(database: LocalDataSource): SettingsFragmentRepository {
//        return SettingsFragmentRepository(database)
//    }
//
//    @Provides
//    @Singleton
//    fun providesViewAccountCredRepo(accountDao: AccountCredDao): ViewAccountCredRepository {
//        return ViewAccountCredRepository(accountDao)
//    }
//
//    @Provides
//    @Singleton
//    fun providesViewBankCredRepo(bankDao: BankCredDao): ViewBankCredRepository {
//        return ViewBankCredRepository(bankDao)
//    }
//
//    @Provides
//    @Singleton
//    fun providesEditAccountCredRepo(accountDao: AccountCredDao): EditAccountCredViewModelRepository {
//        return EditAccountCredViewModelRepository(accountDao)
//    }
//
//    @Provides
//    @Singleton
//    fun providesEditBankAccountRepo(bankDao: BankCredDao): EditBankCredRepository {
//        return EditBankCredRepository(bankDao)
//    }


}