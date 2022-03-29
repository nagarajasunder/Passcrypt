package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.entities.User
import com.geekydroid.passcrypt.enums.MasterPasswordChangeEvent
import com.geekydroid.passcrypt.viewmodels.UpdateMasterPasswordViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UpdateMasterPassword : Fragment(R.layout.fragment_update_master_password) {

    private lateinit var fragmentView: View
    private lateinit var etMasterPassword: TextInputLayout
    private lateinit var fabVerifyPassword: FloatingActionButton
    private var userAuthenticated = false
    private lateinit var user: User

    private val viewModel: UpdateMasterPasswordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view
        setUI()

        fabVerifyPassword.setOnClickListener {
            if (userAuthenticated) {
                getUserInputForNewPassword()
            } else {
                getUserInputForOldPassword()
            }
        }

        viewModel.userProfile.observe(viewLifecycleOwner) { response ->
            user = response
        }

        viewModel.passwordChangeResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                MasterPasswordChangeEvent.USER_AUTHENTICATED -> {
                    userAuthenticated = true
                    etMasterPassword.editText?.hint = getString(R.string.set_new_master_password)
                }
                MasterPasswordChangeEvent.USER_AUTH_ERROR -> {
                    showSnackBar("The master password doesn't match. Please try again")
                }
                else -> {
                    showSnackBar("Master Password changed successfully!")
                    findNavController().navigateUp()
                }
            }
            etMasterPassword.editText?.text?.clear()
        }

    }

    private fun getUserInputForNewPassword() {
        val userEnteredPassword = etMasterPassword.editText?.text.toString()
        if (userEnteredPassword.isEmpty()) {
            showSnackBar("Please enter your new Master password")
        } else {
            viewModel.updateMasterPassword(userEnteredPassword, user)
        }
    }

    private fun getUserInputForOldPassword() {
        val userEnteredPassword = etMasterPassword.editText?.text.toString()
        if (userEnteredPassword.isEmpty()) {
            showSnackBar("Please enter your current Master password")
        } else {
            viewModel.authenticateUser(userEnteredPassword, user)
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(fragmentView, message, Snackbar.LENGTH_LONG).show()
    }

    private fun setUI() {
        etMasterPassword = fragmentView.findViewById(R.id.ed_old_master_password)
        fabVerifyPassword = fragmentView.findViewById(R.id.fab_proceed)
        etMasterPassword.editText?.hint = getString(R.string.enter_you_old_master_password)
    }

}