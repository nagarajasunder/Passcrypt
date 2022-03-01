package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.geekydroid.passcrypt.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AddNewBankCred @Inject constructor() : Fragment(R.layout.fragment_add_new_bank_cred) {

    private lateinit var fragmentView: View
    private lateinit var et_card_number_1: EditText
    private lateinit var et_card_number_2: EditText
    private lateinit var et_card_number_3: EditText
    private lateinit var et_card_number_4: EditText


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentView = view

        setUI()

        et_card_number_1.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty() && text.length > 3) {
                et_card_number_2.requestFocus()
            }
        }

        et_card_number_2.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty() && text.length > 3) {
                et_card_number_3.requestFocus()
            }
        }


        et_card_number_3.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty() && text.length > 3) {
                et_card_number_4.requestFocus()
            }
        }


    }

    private fun setUI() {
        et_card_number_1 = fragmentView.findViewById(R.id.et_card_num_1)
        et_card_number_2 = fragmentView.findViewById(R.id.et_card_num_2)
        et_card_number_3 = fragmentView.findViewById(R.id.et_card_num_3)
        et_card_number_4 = fragmentView.findViewById(R.id.et_card_num_4)

    }

}