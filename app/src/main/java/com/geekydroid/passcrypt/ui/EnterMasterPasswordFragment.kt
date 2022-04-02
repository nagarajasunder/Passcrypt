package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.geekydroid.passcrypt.PasscryptApp
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.Utils.DialogBuilder
import com.geekydroid.passcrypt.datasources.EncryptedDataSource
import com.geekydroid.passcrypt.entities.User
import com.geekydroid.passcrypt.viewmodels.EnterMasterPasswordViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout
import dagger.Lazy
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
    private var userEnteredPass = ""
    private lateinit var waitingDialog: AlertDialog

    @Inject
    lateinit var app: PasscryptApp

    @Inject
    lateinit var database: Lazy<EncryptedDataSource>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs =
            requireActivity().getSharedPreferences("myPrefs", AppCompatActivity.MODE_PRIVATE)
        val isFirstLaunch =
            prefs.getBoolean(app.FIRST_LAUNCH, true)
        if (isFirstLaunch) {
            navigationToSetMasterPassword()
        }


        fragmentView = view
        setUI()




        viewmodel.user.observe(viewLifecycleOwner) {
            if (it != null && it.selfDestructive) {
                currentUser = it
                selfDestructive = true
                if (it.selfDestructiveCount < 0) {
                    showLimitExceededCards()
                } else {
                    numberOfAttempts = it.selfDestructiveCount
                    showSelfDestructiveCards()
                }
            }
        }


        btnSetNewPassword.setOnClickListener {
            val action =
                EnterMasterPasswordFragmentDirections.actionEnterMasterPasswordFragmentToSetMasterPassFragment(
                    app.NAVIGATION_MODE_RESET
                )
            findNavController().navigate(action)
        }



        viewmodel.userAuthFlag.observe(viewLifecycleOwner) { result ->
            if (result) {
                clearMasterPass()
                waitingDialog.dismiss()
                navigateToHome()
            } else {
                userEnteredPass = ""
                etPassword.error = "Master password doesn't match Please try again"
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
                    if (selfDestructive) {
                        currentUser.selfDestructiveCount = numberOfAttempts
                        viewmodel.updateUser(currentUser)
                    }
                    activity?.finish()
                }

            })
    }

    private fun clearMasterPass() {
        app.setMasterPass(userEnteredPass)
        database.get()
        app.clearMasterPass()
        userEnteredPass = ""


    }

    private fun navigationToSetMasterPassword() {
        val action =
            EnterMasterPasswordFragmentDirections.actionEnterMasterPasswordFragmentToSetMasterPassFragment(
                app.NAVIGATION_MODE_NORMAL
            )
        findNavController().navigate(action)
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
        tvAttempts.text =
            getString(R.string.remaining_number_of_attempts, numberOfAttempts.toString())

    }

    private fun showSelfDestructiveCards() {
        attemptsCard.visibility = View.VISIBLE
        warningCard.visibility = View.VISIBLE
        tvAttempts.text =
            getString(R.string.remaining_number_of_attempts, numberOfAttempts.toString())
    }

    private fun navigateToHome() {
        val action =
            EnterMasterPasswordFragmentDirections.actionEnterMasterPasswordFragmentToHomeFragment()
        fragmentView.findNavController().navigate(action)
    }


    private fun verifyUser() {
        userEnteredPass = etPassword.editText?.text?.toString() ?: ""
        if (userEnteredPass.isEmpty()) {
            etPassword.error = "Please enter you Master Password"
        } else {
            showWaitingDialog()
            viewmodel.authenticateUser(userEnteredPass)
        }
    }

    private fun showWaitingDialog() {


        waitingDialog = DialogBuilder(
            requireContext(),
            R.layout.waiting_dialog_view,
            "Please wait.Authenticating user..",
            fragmentView.parent as ViewGroup
        ).getDialog()
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



