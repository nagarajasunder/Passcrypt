package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import com.geekydroid.passcrypt.entities.User
import com.geekydroid.passcrypt.enums.NavigationMode
import com.geekydroid.passcrypt.viewmodels.EnterMasterPasswordViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EnterMasterPasswordFragment : Fragment(R.layout.fragment_enter_master_password) {
    private lateinit var etPassword: TextInputLayout
    private lateinit var btnVerify: Button
    private lateinit var fragmentView: View
    private lateinit var btnSetNewPassword: Button
    private lateinit var warningCard: MaterialCardView
    private lateinit var attemptsCard: MaterialCardView
    private lateinit var limitExceededCard: MaterialCardView
    private lateinit var tvAttempts: TextView
    private val viewmodel: EnterMasterPasswordViewModel by viewModels()
    private var selfDestructive = false
    private var numberOfAttempts = 0
    private lateinit var currentUser: User

    @Inject
    lateinit var database: EncryptedDataSource

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentView = view
        setUI()
        viewmodel.user.observe(viewLifecycleOwner) {
            Log.d("DEBUG:", "onViewCreated: livedata called")
            if (it != null && it.selfDestructive) {
                currentUser = it
                selfDestructive = true
                if (it.selfDestructiveCount < 0) {
                    showLimitExceededCards()
                } else {
                    numberOfAttempts = it.selfDestructiveCount - 1
                    showSelfDestructiveCards()
                }
            }
        }


        btnSetNewPassword.setOnClickListener {
            val mode = NavigationMode.PASSWORD_RESET_MODE
            val action = EnterMasterPasswordFragmentDirections.actionSetMasterFragment()
            
            findNavController().navigate(action)
        }



        viewmodel.userAuthFlag.observe(viewLifecycleOwner) { result ->
            if (result) {
                navigateToHome()
            } else {
                showSnackBar("Master password doesn't match Please try again")
                etPassword.editText?.text?.clear()
                --numberOfAttempts
                if (selfDestructive) {
                    if (numberOfAttempts < 0) {
                        currentUser.selfDestructiveCount = numberOfAttempts
                        viewmodel.updateUser(currentUser)
                        deleteDatabase()
                        showLimitExceededCards()
                    } else {
                        showWarning()
                    }
                }

            }
        }


        btnVerify.setOnClickListener {
            verifyUser()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d("DEBUG:", "handleOnBackPressed: called")
                    if (selfDestructive) {
                        currentUser.selfDestructiveCount = numberOfAttempts
                        viewmodel.updateUser(currentUser)
                    }
                    activity?.finish()
                }

            })
    }

    private fun showLimitExceededCards() {
        etPassword.editText?.isEnabled = false
        btnVerify.isEnabled = false
        attemptsCard.visibility = View.GONE
        limitExceededCard.visibility = View.VISIBLE
        btnSetNewPassword.visibility = View.VISIBLE
    }


    private fun deleteDatabase() {
        requireContext().deleteDatabase("Passcrypt-encrypt.db")
    }


    private fun showWarning() {
        tvAttempts.text = getString(R.string.number_of_attempts, numberOfAttempts.toString())

    }

    private fun showSelfDestructiveCards() {
        attemptsCard.visibility = View.VISIBLE
        warningCard.visibility = View.VISIBLE
        tvAttempts.text = getString(R.string.number_of_attempts, numberOfAttempts.toString())
    }

    private fun navigateToHome() {
        val action =
            EnterMasterPasswordFragmentDirections.actionEnterMasterPasswordFragmentToHomeFragment()
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
        warningCard = fragmentView.findViewById(R.id.warning_card)
        attemptsCard = fragmentView.findViewById(R.id.attempts_card)
        tvAttempts = fragmentView.findViewById(R.id.tv_attempts)
        btnSetNewPassword = fragmentView.findViewById(R.id.btn_set_new_master_password)
        limitExceededCard = fragmentView.findViewById(R.id.limit_crossed_card)
    }


}



