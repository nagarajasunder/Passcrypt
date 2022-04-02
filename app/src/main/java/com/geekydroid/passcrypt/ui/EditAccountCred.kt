package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.entities.AccountCred
import com.geekydroid.passcrypt.viewmodels.EditAccountCredViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditAccountCred : Fragment(R.layout.fragment_edit_account_cred) {

    private lateinit var fragmentView: View
    private lateinit var etSiteName: TextInputLayout
    private lateinit var etUserName: TextInputLayout
    private lateinit var etPassword: TextInputLayout
    private lateinit var etComments: TextInputLayout
    private lateinit var etTitle: TextInputLayout
    private val viewModel: EditAccountCredViewModel by viewModels()
    private val args: EditAccountCredArgs by navArgs()
    private var credId: Int = 0
    private lateinit var cred: AccountCred

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentView = view
        credId = args.credId

        setUI()

        setHasOptionsMenu(true)

        viewModel.getCredByCredId(credId).observe(viewLifecycleOwner) { data ->
            if (data != null) {
                cred = data
                setData()
            }
        }



    }

    private fun setData() {
        etTitle.editText?.setText(cred.title)
        etSiteName.editText?.setText(cred.siteName)
        etUserName.editText?.setText(cred.userName)
        etPassword.editText?.setText(cred.password)
        etComments.editText?.setText(cred.comments)
    }

    private fun getUserInput() {
        cred.title = etTitle.editText?.text.toString()
        cred.siteName = etSiteName.editText?.text.toString()
        cred.userName = etUserName.editText?.text.toString()
        cred.password = etPassword.editText?.text.toString()
        cred.comments = etComments.editText?.text.toString()

        if (cred.title.isEmpty()) {
            etTitle.error = "Title cannot be empty"
        } else {
            viewModel.updateAccountCred(cred)
            showSnackBar("Credential updated successfully!")
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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_cred_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save) {
            getUserInput()
        }
        return true
    }

}