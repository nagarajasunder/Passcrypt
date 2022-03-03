package com.geekydroid.passcrypt.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekydroid.passcrypt.entities.BankCred
import com.geekydroid.passcrypt.repository.AddNewBankCredRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewBankCredViewModel @Inject constructor(private val repo: AddNewBankCredRepository) :
    ViewModel() {

    fun insertBankCred(
        cardNumber: String,
        bankName: String,
        accountNumber: String,
        ifscCode: String,
        customerId: String,
        expiryDate: String,
        cvvNumber: String,
        comments: String,
        cardPin: String
    ) {
        val bankCred = BankCred(
            bankName = bankName,
            cardNumber = cardNumber,
            accountNumber = accountNumber,
            ifscCode = ifscCode,
            customerId = customerId,
            cvv = cvvNumber,
            cardExpiryDate = expiryDate,
            comments = comments,
            updatedOn = System.currentTimeMillis(),
            cardPinNumber = cardPin
        )
        viewModelScope.launch {
            repo.insertBankCred(bankCred)
        }
    }

}