package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.viewmodels.EnterMasterPasswordViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterMasterPasswordFragment : Fragment(R.layout.fragment_enter_master_password) {
    private lateinit var etPassword: TextInputLayout
    private lateinit var btnVerify: Button
    private lateinit var fragmentView: View
    private val viewmodel: EnterMasterPasswordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentView = view
        setUI()

        viewmodel.userAuthFlag.observe(viewLifecycleOwner) { result ->
            if (result) {
                navigateToHome()
            } else {
                showSnackBar("Master password doesn't match Please try again")
                etPassword.editText?.text?.clear()
            }
        }

        btnVerify.setOnClickListener {
            verifyUser()
        }
    }

    private fun navigateToHome() {
//        showSnackBar("Authentication Successful!")
        val action = EnterMasterPasswordFragmentDirections.actionEnterMasterPasswordFragmentToHomeFragment()
        fragmentView.findNavController().navigate(action)
    }



    private fun verifyUser() {
        val passwordText = etPassword.editText?.text?.toString()
        if (passwordText.isNullOrEmpty()) {
            showSnackBar("Please enter your master password")
        } else {
            viewmodel.authenticateUser(passwordText)
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(fragmentView, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setUI() {
        etPassword = fragmentView.findViewById(R.id.ed_password)
        btnVerify = fragmentView.findViewById(R.id.btn_verify_password)
    }
}