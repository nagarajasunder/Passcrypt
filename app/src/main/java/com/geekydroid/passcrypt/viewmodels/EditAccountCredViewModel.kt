package com.geekydroid.passcrypt.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekydroid.passcrypt.entities.AccountCred
import com.geekydroid.passcrypt.repository.EditAccountCredViewModelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditAccountCredViewModel @Inject constructor(private val repo: EditAccountCredViewModelRepository) :
    ViewModel() {

    fun updateAccountCred(cred: AccountCred) {
        viewModelScope.launch {
            repo.updateAccountCred(cred)
        }
    }

    fun getCredByCredId(credId: Int) = repo.getCredByCredId(credId)
}