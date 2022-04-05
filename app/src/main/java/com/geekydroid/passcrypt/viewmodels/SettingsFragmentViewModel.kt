package com.geekydroid.passcrypt.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekydroid.passcrypt.entities.User
import com.geekydroid.passcrypt.repository.SettingsFragmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(private val repository: SettingsFragmentRepository) :
    ViewModel() {


    init {
        Log.d("SETTINGS", ": queried once")
    }

    private val userSettings: LiveData<User> = repository.getUserSettings()

    fun getUserSetting() = userSettings

    fun updateUserSettings(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }
}