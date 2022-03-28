package com.geekydroid.passcrypt.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.viewmodels.SettingsFragmentViewModel
import com.google.android.material.switchmaterial.SwitchMaterial
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {


    private lateinit var fragmentView: View

    private lateinit var selfDestructionSwitch: SwitchMaterial
    private lateinit var tvNumberOfAttempts: TextView
    private lateinit var tvChangeMasterPassword: TextView
    private lateinit var tvRatePasscrypt: TextView
    private lateinit var tvSharePasscrypt: TextView
    private lateinit var tvContactSupport: TextView
    private lateinit var tvOtherApps: TextView

    private val viewModel: SettingsFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentView = view
        setUI()

        selfDestructionSwitch.setOnCheckedChangeListener { _, _ ->
            viewModel.updateSelfDestruction()
        }

        tvRatePasscrypt.setOnClickListener {
            navigateToPlayStore()
        }
        tvOtherApps.setOnClickListener {
            navigateToPlayStoreHome()
        }

        tvSharePasscrypt.setOnClickListener {
            sharePasscrypt()
        }
        tvContactSupport.setOnClickListener {
            contactSupport()
        }

        tvChangeMasterPassword.setOnClickListener {
            updateMasterPassword()
        }
    }

    private fun updateMasterPassword() {
        val action = SettingsFragmentDirections.actionSettingsFragmentToUpdateMasterPassword()
        findNavController().navigate(action)
    }

    private fun contactSupport() {
        val intent =
            Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "gowthamvj15@gmail.com", null))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Support Request for Passcrypt")
        startActivity(Intent.createChooser(intent, null))

    }

    private fun sharePasscrypt() {
        val shareText = getString(R.string.share_text)
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, shareText)
        startActivity(Intent.createChooser(intent, "Share"))
    }

    private fun navigateToPlayStore() {
        val link =
            Uri.parse("https://play.google.com/store/apps/details?id=com.geekydroid.passcrypt")
        val intent = Intent(Intent.ACTION_VIEW, link)
        startActivity(intent)
    }

    private fun navigateToPlayStoreHome() {
        val link = Uri.parse("https://play.google.com/store/apps/dev?id=7436071758058984589")
        val intent = Intent(Intent.ACTION_VIEW, link)
        startActivity(intent)
    }

    private fun setUI() {
        selfDestructionSwitch = fragmentView.findViewById(R.id.switch_self_destruction)
        tvNumberOfAttempts = fragmentView.findViewById(R.id.tv_number_of_attempts)
        tvChangeMasterPassword = fragmentView.findViewById(R.id.tv_change_master_password)
        tvRatePasscrypt = fragmentView.findViewById(R.id.tv_rate_passcrypt)
        tvSharePasscrypt = fragmentView.findViewById(R.id.tv_share_passcrypt)
        tvContactSupport = fragmentView.findViewById(R.id.tv_contact_support)
        tvOtherApps = fragmentView.findViewById(R.id.tv_other_products)
    }
}