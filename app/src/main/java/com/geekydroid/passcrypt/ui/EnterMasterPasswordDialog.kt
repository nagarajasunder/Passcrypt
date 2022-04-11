package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.listeners.GenericOnClickListener
import com.google.android.material.textfield.TextInputLayout

class EnterMasterPasswordDialog(private val onClickListener: GenericOnClickListener<Any>) :
    DialogFragment() {

    private lateinit var etMasterPassword: TextInputLayout
    private lateinit var btnVerify: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.enter_master_password_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUI(view)

        btnVerify.setOnClickListener {
            val password = etMasterPassword.editText!!.text.toString()
            if (password.isEmpty()) {
                etMasterPassword.editText!!.error = "Please enter the master password"
            } else {
                onClickListener.onClick(password)
                dismiss()
            }
        }
    }

    private fun setUI(view: View) {

        etMasterPassword = view.findViewById(R.id.et_master_password)
        btnVerify = view.findViewById(R.id.btn_verify)
    }
}