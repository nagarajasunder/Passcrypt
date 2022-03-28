package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.geekydroid.passcrypt.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UpdateMasterPassword : Fragment(R.layout.fragment_update_master_password) {

    private lateinit var fragmentView: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view
    }

}