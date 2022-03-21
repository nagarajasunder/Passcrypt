package com.geekydroid.passcrypt.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekydroid.passcrypt.entities.BankCred
import com.geekydroid.passcrypt.entities.Card
import com.geekydroid.passcrypt.enums.Result
import com.geekydroid.passcrypt.repository.AddNewBankCredRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewBankCredViewModel @Inject constructor(private val repo: AddNewBankCredRepository) :
    ViewModel() {


    val successFlag: MutableLiveData<Result> = MutableLiveData(null)

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

        viewModelScope.launch {
            val bankCred = BankCred(
                bankName = bankName,
                accountNumber = accountNumber,
                ifscCode = ifscCode,
                customerId = customerId,
                comments = comments,
                updatedOn = System.currentTimeMillis(),
            )
            if (cardNullCheck(cardNumber, expiryDate, cvvNumber, cardPin)) {
                repo.insertBank(bankCred)
                successFlag.postValue(Result.SUCCESS)
            } else {
                val cardCred = Card(
                    cardNumber = cardNumber,
                    cvv = cvvNumber,
                    cardPinNumber = cardPin,
                    cardExpiryDate = expiryDate,
                    bankId = 0,
                    updatedOn = System.currentTimeMillis()
                )
                val result = repo.insertBankCred(bankCred, cardCred)
                if (result == -1L) {
                    successFlag.postValue(Result.ERROR)
                } else {
                    successFlag.postValue(Result.SUCCESS)
                }
            }
        }


    }

    private fun cardNullCheck(
        cardNumber: String,
        expiryDate: String,
        cvvNumber: String,
        cardPin: String
    ): Boolean {

        if (cardNumber.contentEquals(":::") && expiryDate.isEmpty() && cvvNumber.isEmpty() && cardPin.isEmpty()) {
            return true
        }
        return false
    }

}