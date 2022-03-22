package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.entities.BankCred
import com.geekydroid.passcrypt.entities.Card
import com.geekydroid.passcrypt.viewmodels.EditBankAccountViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditBankAccountCred : Fragment(R.layout.fragment_edit_bank_account_cred) {

    private lateinit var fragmentView: View
    private lateinit var etCardNumber1: TextInputLayout
    private lateinit var etCardNumber2: TextInputLayout
    private lateinit var etCardNumber3: TextInputLayout
    private lateinit var etCardNumber4: TextInputLayout
    private lateinit var etBankName: TextInputLayout
    private lateinit var etAccountNumber: TextInputLayout
    private lateinit var etIfscCode: TextInputLayout
    private lateinit var etCustomerId: TextInputLayout
    private lateinit var fabEdit: FloatingActionButton
    private lateinit var etExpiryDate: TextInputLayout
    private lateinit var etCvv: TextInputLayout
    private lateinit var etCardPin: TextInputLayout
    private lateinit var etComments: TextInputLayout

    private val args: EditBankAccountCredArgs by navArgs()
    private var bankCredId: Int = 0
    private val viewmodel: EditBankAccountViewModel by viewModels()
    private lateinit var bankCred: BankCred
    private var cardCred: List<Card> = listOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentView = view
        bankCredId = args.credId
        setUI()

        viewmodel.getBankAndCardCreds(bankCredId).observe(viewLifecycleOwner) { response ->

            if (response.first != null) {
                bankCred = response.first!!
                setBankData(bankCred)
            }
            if (response.second != null && response.second!!.isNotEmpty()) {
                cardCred = response.second!!
                setCardData(cardCred)
            }

        }

        fabEdit.setOnClickListener {
            validateUserData()
        }


    }

    private fun setCardData(cardCred: List<Card>) {

        val currentCard = cardCred[0]
        if (currentCard.cardNumber.isNotEmpty()) {
            val cardSplit = currentCard.cardNumber.split(":")
            etCardNumber1.editText?.setText(cardSplit[0])
            etCardNumber2.editText?.setText(cardSplit[1])
            etCardNumber3.editText?.setText(cardSplit[2])
            etCardNumber4.editText?.setText(cardSplit[3])
        }
        etCardPin.editText?.setText(currentCard.cardPinNumber)
        etCvv.editText?.setText(currentCard.cvv)
        etExpiryDate.editText?.setText(currentCard.cardExpiryDate)

    }


    private fun setBankData(bankCred: BankCred) {

        etBankName.editText?.setText(bankCred.bankName)
        etAccountNumber.editText?.setText(bankCred.accountNumber)
        etIfscCode.editText?.setText(bankCred.ifscCode)
        etCustomerId.editText?.setText(bankCred.customerId)
        etComments.editText?.setText(bankCred.comments)
    }


    private fun setUI() {
        etCardNumber1 = fragmentView.findViewById(R.id.et_card_num_1)
        etCardNumber2 = fragmentView.findViewById(R.id.et_card_num_2)
        etCardNumber3 = fragmentView.findViewById(R.id.et_card_num_3)
        etCardNumber4 = fragmentView.findViewById(R.id.et_card_num_4)

        etBankName = fragmentView.findViewById(R.id.et_bank_name)
        etAccountNumber = fragmentView.findViewById(R.id.et_account_number)
        etIfscCode = fragmentView.findViewById(R.id.et_ifsc_code)
        etCustomerId = fragmentView.findViewById(R.id.et_customer_id)
        fabEdit = fragmentView.findViewById(R.id.fab_edit)
        etExpiryDate = fragmentView.findViewById(R.id.et_expiry_date)
        etCvv = fragmentView.findViewById(R.id.et_cvv_number)
        etCardPin = fragmentView.findViewById(R.id.et_pin_number)
        etComments = fragmentView.findViewById(R.id.et_comments)

    }

    private fun validateUserData() {
        var cardNumber = StringBuilder().append(
            etCardNumber1.editText?.text.toString().trim(),
            ":",
            etCardNumber2.editText?.text.toString().trim(),
            ":",
            etCardNumber3.editText?.text.toString().trim(),
            ":",
            etCardNumber4.editText?.text.toString().trim()
        ).toString()

        cardNumber = if (cardNumber.contentEquals("::::")) "" else cardNumber


        bankCred.bankName = etBankName.editText?.text.toString().trim()
        bankCred.accountNumber = etAccountNumber.editText?.text.toString().trim()
        bankCred.ifscCode = etIfscCode.editText?.text.toString().trim()
        bankCred.customerId = etCustomerId.editText?.text.toString().trim()
        val cardExpiryDate = etExpiryDate.editText?.text.toString().trim()
        val cvv = etCvv.editText?.text.toString().trim()
        val cardPinNumber = etCardPin.editText?.text.toString().trim()
        bankCred.comments = etComments.editText?.text.toString().trim()

        when {
            bankCred.bankName.isEmpty() -> {
                showSnackBar("Bank Name cannot be empty")
                etBankName.editText?.text?.clear()
            }
            bankCred.bankName.length > 100 -> {
                etBankName.error = "Bank name should have up to 100 characters"
            }
            else -> {
                viewmodel.updateBankAndCardCred(
                    bankCred = bankCred,
                    cardNumber = cardNumber,
                    cardPin = cardPinNumber,
                    cardExpiryDate = cardExpiryDate,
                    cardCvv = cvv,
                    cardId = if (cardCred.isNullOrEmpty()) -1 else cardCred[0].cardId
                )

                showSnackBar("Bank credentials updated successfully!")
                fragmentView.findNavController().navigateUp()
            }
        }

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(fragmentView, message, Snackbar.LENGTH_SHORT).show()
    }
}