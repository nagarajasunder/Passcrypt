package com.geekydroid.passcrypt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.geekydroid.passcrypt.entities.BankCred
import com.geekydroid.passcrypt.entities.Card
import com.geekydroid.passcrypt.repository.EditBankCredRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditBankAccountViewModel @Inject constructor(private val repo: EditBankCredRepository) :
    ViewModel() {

    private suspend fun getBankCredById(credId: Int) = repo.getBankCred(credId)

    private suspend fun getCardByBankId(bankId: Int) = repo.getCardCredByBankAccountId(bankId)

    fun getBankAndCardCreds(bankId: Int): LiveData<Pair<BankCred?, List<Card>?>> {
        return liveData {
            val bankCred = getBankCredById(bankId)
            val cardCred = getCardByBankId(bankId)
            emit(Pair(bankCred, cardCred))
        }
    }

    fun updateBankAndCardCred(
        bankCred: BankCred,
        cardNumber: String,
        cardPin: String,
        cardCvv: String,
        cardExpiryDate: String,
        cardId: Int
    ) {
        viewModelScope.launch {
            repo.updateBank(bankCred)
            if (cardId == -1 && cardAdded(cardNumber, cardPin, cardCvv, cardExpiryDate)) {
                val card = Card(
                    cardNumber = cardNumber,
                    cardPinNumber = cardPin,
                    cardExpiryDate = cardExpiryDate,
                    cvv = cardCvv,
                    bankId = bankCred.credId,
                    updatedOn = System.currentTimeMillis()
                )
                repo.insertNewCard(card)
            } else {
                val card = Card(
                    cardId = cardId,
                    cardNumber = cardNumber,
                    cardPinNumber = cardPin,
                    cardExpiryDate = cardExpiryDate,
                    cvv = cardCvv,
                    bankId = bankCred.credId,
                    updatedOn = System.currentTimeMillis()
                )
                repo.updateCard(card)
            }
        }
    }

    private fun cardAdded(
        cardNumber: String,
        cardPin: String,
        cardCvv: String,
        cardExpiryDate: String
    ): Boolean {
        return !(cardNumber.isEmpty() && cardPin.isEmpty() && cardCvv.isEmpty() && cardExpiryDate.isEmpty())
    }


}