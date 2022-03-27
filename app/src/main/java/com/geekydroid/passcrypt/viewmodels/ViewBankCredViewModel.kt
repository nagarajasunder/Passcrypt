package com.geekydroid.passcrypt.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.geekydroid.passcrypt.entities.BankCred
import com.geekydroid.passcrypt.entities.Card
import com.geekydroid.passcrypt.repository.ViewBankCredRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewBankCredViewModel @Inject constructor(private val repo: ViewBankCredRepository) :
    ViewModel() {


    fun getBankCredWithCard(credId: Int): LiveData<Pair<BankCred, List<Card>>?> {
        return liveData {
            val response = repo.getBankCredWithCards(credId)
            emit(response)
        }
    }

    fun removeFromFavorites(bankCred: BankCred) {
        viewModelScope.launch {
            bankCred.isFavorite = false
            repo.updateBank(bankCred)
        }
    }


    fun addToFavorites(bankCred: BankCred) {
        viewModelScope.launch {
            bankCred.isFavorite = true
            repo.updateBank(bankCred)
        }
    }

    fun deleteCredential(bankCred: BankCred) {
        viewModelScope.launch {
            repo.deleteBankCredential(bankCred)
        }
    }
}