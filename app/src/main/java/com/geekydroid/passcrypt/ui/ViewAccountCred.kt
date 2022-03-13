package com.geekydroid.passcrypt.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geekydroid.passcrypt.R


class ViewAccountCred : Fragment(R.layout.fragment_view_account_cred) {

    private lateinit var fragmentView: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view
    }

}