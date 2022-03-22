package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.enums.Result
import com.geekydroid.passcrypt.viewmodels.AddNewBankCredViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AddNewBankCred @Inject constructor() : Fragment(R.layout.fragment_add_new_bank_cred) {

    private lateinit var fragmentView: View
    private lateinit var etCardNumber1: TextInputLayout
    private lateinit var etCardNumber2: TextInputLayout
    private lateinit var etCardNumber3: TextInputLayout
    private lateinit var etCardNumber4: TextInputLayout
    private lateinit var etBankName: TextInputLayout
    private lateinit var etAccountNumber: TextInputLayout
    private lateinit var etIfscCode: TextInputLayout
    private lateinit var etCustomerId: TextInputLayout
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var etExpiryDate: TextInputLayout
    private lateinit var etCvv: TextInputLayout
    private lateinit var etCardPin: TextInputLayout
    private lateinit var etComments: TextInputLayout
    private val viewmodel: AddNewBankCredViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentView = view

        setUI()



        viewmodel.successFlag.observe(viewLifecycleOwner) { result ->
            if (result != null && result == Result.ERROR) {
                showSnackBar("Unable to create bank account. Please try again")
            }
        }

        etCardNumber1.editText?.doOnTextChanged { text, _, _, _ ->
            etCardNumber1.editText?.hint = ""
            if (!text.isNullOrEmpty() && text.length > 3) {
                etCardNumber2.requestFocus()
            }
        }

        etCardNumber2.editText?.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty() && text.length > 3) {
                etCardNumber3.requestFocus()
            }
        }


        etCardNumber3.editText?.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty() && text.length > 3) {
                etCardNumber4.requestFocus()
            }
        }

        fabAdd.setOnClickListener {
            validateUserData()
        }


    }

    private fun validateUserData() {
        val cardNumber = StringBuilder().append(
            etCardNumber1.editText?.text.toString().trim(),
            ":",
            etCardNumber2.editText?.text.toString().trim(),
            ":",
            etCardNumber3.editText?.text.toString().trim(),
            ":",
            etCardNumber4.editText?.text.toString().trim()
        ).toString()
        val bankName = etBankName.editText?.text.toString().trim()
        val accountNumber = etAccountNumber.editText?.text.toString().trim()
        val ifscCode = etIfscCode.editText?.text.toString().trim()
        val customerId = etCustomerId.editText?.text.toString().trim()
        val expiryDate = etExpiryDate.editText?.text.toString().trim()
        val cvvNumber = etCvv.editText?.text.toString().trim()
        val cardPin = etCardPin.editText?.text.toString().trim()
        val comments = etComments.editText?.text.toString().trim()

        when {
            bankName.isEmpty() -> {
                showSnackBar("Bank Name cannot be empty")
                etBankName.editText?.text?.clear()
            }
            bankName.length > 100 -> {
                etBankName.error = "Bank name should have up to 100 characters"
            }
            else -> {
                viewmodel.insertBankCred(
                    cardNumber,
                    bankName,
                    accountNumber,
                    ifscCode,
                    customerId,
                    expiryDate,
                    cvvNumber,
                    comments,
                    cardPin
                )

                showSnackBar("Bank credentials saved successfully!")
                fragmentView.findNavController().navigateUp()
            }
        }

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(fragmentView, message, Snackbar.LENGTH_SHORT).show()
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
        fabAdd = fragmentView.findViewById(R.id.fab_add)
        etExpiryDate = fragmentView.findViewById(R.id.et_expiry_date)
        etCvv = fragmentView.findViewById(R.id.et_cvv_number)
        etCardPin = fragmentView.findViewById(R.id.et_pin_number)
        etComments = fragmentView.findViewById(R.id.et_comments)

    }

}