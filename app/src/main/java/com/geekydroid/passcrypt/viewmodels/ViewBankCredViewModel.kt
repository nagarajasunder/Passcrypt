package com.geekydroid.passcrypt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekydroid.passcrypt.entities.BankCred
import com.geekydroid.passcrypt.entities.Card
import com.geekydroid.passcrypt.repository.ViewBankCredRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewBankCredViewModel @Inject constructor(private val repo: ViewBankCredRepository) :
    ViewModel() {

    fun getBankCredWithCard(credId: Int): LiveData<Pair<BankCred, List<Card>>>? {
        var response: LiveData<Pair<BankCred, List<Card>>>? = null
        viewModelScope.launch {
            response = async {
                fetchBankCredWithCard(credId)
            }.await()
        }
        return response
    }

    suspend fun fetchBankCredWithCard(credId: Int) = repo.getBankCredWithCards(credId)

}