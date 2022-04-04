package com.geekydroid.passcrypt.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekydroid.passcrypt.repository.SettingsFragmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(private val repository: SettingsFragmentRepository) :
    ViewModel() {

    val userSettings = repository.getUserSettings()

    fun updateSelfDestruction(switch: Boolean) {
        viewModelScope.launch {
            repository.updateSelfDestruction(switch)
        }
    }
}