package com.geekydroid.passcrypt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.geekydroid.passcrypt.entities.AccountCred
import com.geekydroid.passcrypt.repository.ViewAccountCredRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewAccountCredViewModel @Inject constructor(private val repo: ViewAccountCredRepository) :
    ViewModel() {

    fun getAccountCred(credId: Int) = liveData<AccountCred> {
        val cred = repo.getAccountCredById(credId)
        emit(cred)
    }
}