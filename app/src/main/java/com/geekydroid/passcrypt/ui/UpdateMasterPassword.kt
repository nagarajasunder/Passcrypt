package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.entities.User
import com.geekydroid.passcrypt.enums.MasterPasswordChangeEvent
import com.geekydroid.passcrypt.viewmodels.UpdateMasterPasswordViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UpdateMasterPassword : Fragment(R.layout.fragment_update_master_password) {

    private lateinit var fragmentView: View
    private lateinit var etMasterPassword: TextInputLayout
    private lateinit var etNewMasterPassword: TextInputLayout
    private lateinit var etConfirmMasterPassword: TextInputLayout
    private lateinit var btnUpdateMasterPassword: Button
    private lateinit var btnVerifyMasterPassword: Button
    private lateinit var user: User
    private var oldPassword: String = ""
    private var newPassword = ""
    private val viewModel: UpdateMasterPasswordViewModel by viewModels()

    private lateinit var oldPasswordCard: MaterialCardView
    private lateinit var newPasswordCard: MaterialCardView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view
        setUI()

        btnVerifyMasterPassword.setOnClickListener {
            getUserInputForOldPassword()
        }

        btnUpdateMasterPassword.setOnClickListener {
            getUserInputForNewPassword()
        }

        viewModel.userProfile.observe(viewLifecycleOwner) { response ->
            user = response
        }

        viewModel.passwordChangeResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                MasterPasswordChangeEvent.USER_AUTHENTICATED -> {
                    animateCard()
                }
                MasterPasswordChangeEvent.USER_AUTH_ERROR -> {
                    etMasterPassword.error = "The master password doesn't match. Please try again"
                }
                else -> {
                    showSnackBar("Master Password changed successfully!")
                    findNavController().navigateUp()
                }
            }
            etMasterPassword.editText?.text?.clear()
        }


    }

    private fun animateCard() {

        val slideRightOut = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_right)
        oldPasswordCard.startAnimation(slideRightOut)

        newPasswordCard.visibility = View.VISIBLE
        val slideLeftIn = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_left)
        newPasswordCard.startAnimation(slideLeftIn)
        oldPasswordCard.visibility = View.GONE

    }

    private fun getUserInputForNewPassword() {
        newPassword = etNewMasterPassword.editText?.text.toString()
        val confirmPassword = etConfirmMasterPassword.editText?.text.toString()

        when {
            newPassword.isEmpty() -> {
                etNewMasterPassword.error = "Please enter your new Master Password"
            }
            confirmPassword.isEmpty() -> {
                etConfirmMasterPassword.error = "Please confirm your new Master Password"
            }
            newPassword != confirmPassword -> {
                etConfirmMasterPassword.error = "Passwords doesn't match"
            }
            else -> {
                viewModel.updateMasterPassword(
                    userEnteredPassword = newPassword,
                    user
                )
            }
        }

    }


    private fun getUserInputForOldPassword() {
        oldPassword = etMasterPassword.editText?.text.toString()
        if (oldPassword.isEmpty()) {
            etMasterPassword.error = "Please enter your current Master password"
        } else {
            viewModel.authenticateUser(oldPassword, user)
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(fragmentView, message, Snackbar.LENGTH_LONG).show()
    }

    private fun setUI() {
        etMasterPassword = fragmentView.findViewById(R.id.old_master_password)
        etNewMasterPassword = fragmentView.findViewById(R.id.new_master_password)
        etConfirmMasterPassword = fragmentView.findViewById(R.id.confirm_master_password)

        btnVerifyMasterPassword = fragmentView.findViewById(R.id.btn_verify)
        btnUpdateMasterPassword = fragmentView.findViewById(R.id.btn_update)
        oldPasswordCard = fragmentView.findViewById(R.id.old_password_card)
        newPasswordCard = fragmentView.findViewById(R.id.new_password_card)

    }

}