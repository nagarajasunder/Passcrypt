package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.viewmodels.SettingsFragmentViewModel
import com.google.android.material.switchmaterial.SwitchMaterial
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var fragmentView: View
    private lateinit var selfDestructionSwitch: SwitchMaterial
    private val viewModel: SettingsFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentView = view
        setUI()

        selfDestructionSwitch.setOnCheckedChangeListener { _, _ ->
            viewModel.updateSelfDestruction()
        }
    }

    private fun setUI() {
        selfDestructionSwitch = fragmentView.findViewById(R.id.switch_self_destruction)
    }
}