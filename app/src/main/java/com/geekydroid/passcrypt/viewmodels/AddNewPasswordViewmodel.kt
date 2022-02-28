package com.geekydroid.passcrypt.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekydroid.passcrypt.entities.AccountCred
import com.geekydroid.passcrypt.repository.AddNewPasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewPasswordViewmodel @Inject constructor(private val repo: AddNewPasswordRepository) :
    ViewModel() {

    fun storePassword(siteName: String, userName: String, password: String, comments: String) {
        viewModelScope.launch {
            val cred = AccountCred(
                siteName = siteName,
                userName = userName,
                password = password,
                updatedOn = System.currentTimeMillis(),
                comments = comments
            )
            repo.insertCred(cred)
        }
    }
}