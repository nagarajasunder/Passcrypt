package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.geekydroid.passcrypt.PasscryptApp
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.enums.NavigationMode
import com.geekydroid.passcrypt.viewmodels.SetMasterPasswordViewmodel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetMasterPassFragment : Fragment(R.layout.fragment_set_master_pass) {
    private lateinit var etPassword: TextInputLayout
    private lateinit var etConfirmPassword: TextInputLayout
    private lateinit var btnSetup: Button
    private lateinit var fragmentView: View
    private val viewmodel: SetMasterPasswordViewmodel by viewModels()
    private lateinit var NAVIGATION_MODE: NavigationMode

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NAVIGATION_MODE = NavigationMode.PASSWORD_RESET_MODE


        fragmentView = view
        setUI()
        viewmodel.createUserProcessFlag.observe(viewLifecycleOwner)
        { result ->
            if (result == true) {
                showSnackBar("Master password set successfully!!")
                navigateToHome()

            } else {
                showSnackBar("Cannot set Master password please try again :(")
                clearData()
            }
        }

        btnSetup.setOnClickListener {

            setUpMasterPassword()
        }
    }


    private fun navigateToHome() {

        val action =
            SetMasterPassFragmentDirections.actionSetMasterPassFragmentToHomeFragment()
        fragmentView.findNavController().navigate(action)
    }

    private fun clearData() {
        etPassword.editText?.text?.clear()
        etConfirmPassword.editText?.text?.clear()
    }

    private fun setUpMasterPassword() {
        val passwordText = etPassword.editText!!.text.toString()
        val confirmPasswordText = etConfirmPassword.editText!!.text.toString()
        if (passwordText.isEmpty() || confirmPasswordText.isEmpty()) {
            showSnackBar("Please fill all the fields.")
        } else if (!passwordText.contentEquals(confirmPasswordText)) {
            showSnackBar("Passwords doesn't match.")
        } else {
            viewmodel.createUser(passwordText, NAVIGATION_MODE)
            updatePrefs()
        }
    }

    private fun updatePrefs() {
        val prefs = requireContext().getSharedPreferences("myPrefs", AppCompatActivity.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean((requireActivity().application as PasscryptApp).FIRST_LAUNCH, false)
        editor.apply()
    }


    private fun showSnackBar(message: String) {
        Snackbar.make(fragmentView, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setUI() {
        etPassword = fragmentView.findViewById(R.id.ed_master_password)
        etConfirmPassword = fragmentView.findViewById(R.id.ed_confirm_password)
        btnSetup = fragmentView.findViewById(R.id.btn_set_password)
    }


}