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
        val bankCred = BankCred(
            bankName = bankName,
            accountNumber = accountNumber,
            ifscCode = ifscCode,
            customerId = customerId,
            comments = comments,
            updatedOn = System.currentTimeMillis(),
        )

        val cardCred = Card(
            cardNumber = cardNumber,
            cvv = cvvNumber,
            cardPinNumber = cardPin,
            cardExpiryDate = expiryDate,
            updatedOn = System.currentTimeMillis(),
            bankId = -1
        )
        viewModelScope.launch {
            val result = repo.insertBankCred(bankCred, cardCred)
            if (result == -1L) {
                successFlag.postValue(Result.ERROR)
            } else {
                successFlag.postValue(Result.SUCCESS)
            }
        }
    }

}