package com.geekydroid.passcrypt.Utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

class UserPreferences(
    private val context: Context
) {

    private val applicationContext = context.applicationContext
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "userPreferences")
    val SORT_BY_NAME_ASC = booleanPreferencesKey("SORT_BY_NAME_ASC")
}