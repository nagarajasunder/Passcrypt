package com.geekydroid.passcrypt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekydroid.passcrypt.entities.AccountCred
import com.geekydroid.passcrypt.repository.ViewAccountCredRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewAccountCredViewModel @Inject constructor(private val repo: ViewAccountCredRepository) :
    ViewModel() {

    fun getAccountCred(credId: Int): LiveData<AccountCred> {

        return repo.getAccountCredById(credId)

    }


    fun addToFavorites(accountCred: AccountCred) {
        viewModelScope.launch {
            accountCred.isFavourite = true
            repo.addToFavorites(accountCred)
        }
    }

    fun removeFromFavorites(accountCred: AccountCred) {
        viewModelScope.launch {
            accountCred.isFavourite = false
            repo.addToFavorites(accountCred)
        }
    }
}