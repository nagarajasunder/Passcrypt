package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.viewmodels.AddNewPasswordViewmodel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewPassword : Fragment(R.layout.fragment_add_new_password) {

    private lateinit var fragmentView: View
    private lateinit var etSiteName: TextInputLayout
    private lateinit var etUserName: TextInputLayout
    private lateinit var etPassword: TextInputLayout
    private lateinit var etComments: TextInputLayout
    private lateinit var etTitle: TextInputLayout
    private lateinit var btnAdd: FloatingActionButton
    private val viewmodel: AddNewPasswordViewmodel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentView = view
        setUI()
        btnAdd.setOnClickListener {
            getUserInput()
        }
    }

    private fun getUserInput() {
        val title = etTitle.editText?.text.toString()
        val siteName = etSiteName.editText?.text.toString()
        val userName = etUserName.editText?.text.toString()
        val passwordText = etPassword.editText?.text.toString()
        val commentsText = etComments.editText?.text.toString()

        if (title.isEmpty()) {
            etTitle.error = "Title cannot be empty"
        } else {
            viewmodel.storePassword(title, siteName, userName, passwordText, commentsText)
            showSnackBar("Account password saved securely!")
            fragmentView.findNavController().navigateUp()
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(fragmentView, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setUI() {

        etSiteName = fragmentView.findViewById(R.id.et_site_name)
        etUserName = fragmentView.findViewById(R.id.et_user_name)
        etPassword = fragmentView.findViewById(R.id.et_password)
        etComments = fragmentView.findViewById(R.id.et_comments)
        etTitle = fragmentView.findViewById(R.id.et_title)
        btnAdd = fragmentView.findViewById(R.id.btn_add_password)
    }
}