package com.geekydroid.passcrypt.Utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.geekydroid.passcrypt.PasscryptApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    private val context: PasscryptApp
) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "userPreferences")
    val SORT_PREFERENCES = stringPreferencesKey("SORT_PREFERENCES")

    val sortPreferences: Flow<String> = context.datastore.data.catch { exception ->

        if (exception is IOException) {
            emptyPreferences()
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[SORT_PREFERENCES] ?: SortPreference.SORT_BY_DATE_DESC.name
    }

    suspend fun updateSortPreferences(preference: SortPreference) {
        context.datastore.edit { settings ->
            settings[SORT_PREFERENCES] = preference.name
        }
    }
}

enum class SortPreference {
    SORT_BY_NAME_ASC,
    SORT_BY_NAME_DESC,
    SORT_BY_DATE_ASC,
    SORT_BY_DATE_DESC
}