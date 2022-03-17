package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.viewmodels.ViewBankCredViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "ViewBankCred"

@AndroidEntryPoint
class ViewBankCred : Fragment(R.layout.fragment_view_bank_cred) {

    private lateinit var fragmentView: View
    private val args: ViewBankCredArgs by navArgs()
    private val ViewModel: ViewBankCredViewModel by viewModels()
    private var credId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentView = view
        credId = args.credId
        setUI()

        ViewModel.getBankCredWithCard(credId)?.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: ${it.first} ${it.second}")
        }
    }

    private fun setUI() {

    }


}