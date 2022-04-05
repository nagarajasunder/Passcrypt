package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.geekydroid.passcrypt.PasscryptApp
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.Utils.DialogBuilder
import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import com.geekydroid.passcrypt.enums.NavigationMode
import com.geekydroid.passcrypt.viewmodels.SetMasterPasswordViewmodel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetMasterPassFragment : Fragment(R.layout.fragment_set_master_pass) {
    private lateinit var etPassword: TextInputLayout
    private lateinit var etConfirmPassword: TextInputLayout
    private lateinit var btnSetup: Button
    private lateinit var fragmentView: View
    private val viewmodel: SetMasterPasswordViewmodel by viewModels()
    private lateinit var NAVIGATION_MODE: String
    private val args: SetMasterPassFragmentArgs by navArgs()
    private var userEnteredPass = ""

    private lateinit var waitingDialog: AlertDialog

    @Inject
    lateinit var database: Lazy<EncryptedDataSource>

    @Inject
    lateinit var app: PasscryptApp


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NAVIGATION_MODE = args.mode ?: app.NAVIGATION_MODE_NORMAL

        fragmentView = view
        setUI()
        viewmodel.createUserProcessFlag.observe(viewLifecycleOwner)
        { result ->
            if (result == true) {
                clearMasterPass()
                waitingDialog.dismiss()
                showSnackBar("Master password set successfully!!")
                navigateToHome()

            } else {
                waitingDialog.dismiss()
                showSnackBar("Cannot set Master password please try again.")
                clearData()
            }
        }

        btnSetup.setOnClickListener {

            setUpMasterPassword()
        }
    }

    private fun clearMasterPass() {
        app.setMasterPass(userEnteredPass)
        database.get()
        app.clearMasterPass()
        userEnteredPass = ""
    }


    private fun navigateToHome() {

        val action =
            SetMasterPassFragmentDirections.actionSetMasterPassFragmentToHomeFragment()
        fragmentView.findNavController().navigate(action)
    }

    private fun clearData() {
        userEnteredPass = ""
        etPassword.editText?.text?.clear()
        etConfirmPassword.editText?.text?.clear()
    }

    private fun setUpMasterPassword() {
        userEnteredPass = etPassword.editText!!.text.toString()
        val confirmPasswordText = etConfirmPassword.editText!!.text.toString()

        if (userEnteredPass.isEmpty()) {
            etPassword.error = "Please fill all the fields"
        } else if (confirmPasswordText.isEmpty()) {
            etConfirmPassword.error = "Please fill all the fields"
        } else if (!userEnteredPass.contentEquals(confirmPasswordText)) {
            etConfirmPassword.error = "Passwords doesn't match"
//            showSnackBar("Passwords doesn't match.")
        } else {

            showWaitingDialog()

            viewmodel.createUser(
                userEnteredPass,
                if (NAVIGATION_MODE == app.NAVIGATION_MODE_RESET) NavigationMode.PASSWORD_RESET_MODE else NavigationMode.NORMAL_MODE
            )
            updatePrefs()
        }
    }

    private fun showWaitingDialog() {
        waitingDialog = DialogBuilder(
            requireContext(),
            R.layout.waiting_dialog_view,
            "Please Wait.Setting up Master Password...",
            fragmentView.parent as ViewGroup
        ).getDialog()
        waitingDialog.show()


    }

    private fun updatePrefs() {
        val prefs = requireContext().getSharedPreferences("myPrefs", AppCompatActivity.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(app.FIRST_LAUNCH, false)
        editor.apply()
    }


    private fun showSnackBar(message: String) {
        Snackbar.make(fragmentView, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setUI() {
        etPassword = fragmentView.findViewById(R.id.ed_master_password)
        etConfirmPassword = fragmentView.findViewById(R.id.ed_confirm_password)
        btnSetup = fragmentView.findViewById(R.id.btn_set_password)
        etPassword.requestFocus()
    }


}