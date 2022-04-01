package com.geekydroid.passcrypt.di

import android.app.Application
import androidx.room.Room
import com.geekydroid.passcrypt.PasscryptApp
import com.geekydroid.passcrypt.dao.AccountCredDao
import com.geekydroid.passcrypt.dao.BankCredDao
import com.geekydroid.passcrypt.dao.UserDao
import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import com.geekydroid.passcrypt.datasources.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {


    @Provides
    @Singleton
    fun providesApplication(app: Application) = (app as PasscryptApp)

    @Provides
    @Singleton
    @Named("MASTER_PASS")
    fun providePass(app: PasscryptApp) = app.getMasterPass()

    @Provides
    @Singleton
    fun providesDatabase(app: Application): LocalDataSource {
        return Room.databaseBuilder(
            app,
            LocalDataSource::class.java,
            "Passcrypt.db"
        )
            .build()
    }


    @Provides
    @Singleton
    fun providesEncryptedDataSource(
        app: Application,
        @Named("MASTER_PASS") pass: String
    ): EncryptedDataSource {

        val factory = SupportFactory(SQLiteDatabase.getBytes(pass.toCharArray()))
        return Room.databaseBuilder(
            app,
            EncryptedDataSource::class.java,
            "Passcrypt_encrypt.db"
        ).openHelperFactory(factory)
            .build()
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
