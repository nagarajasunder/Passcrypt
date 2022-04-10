package com.geekydroid.passcrypt.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.geekydroid.passcrypt.PasscryptApp
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.Utils.HashingUtils
import com.geekydroid.passcrypt.entities.User
import com.geekydroid.passcrypt.enums.Result
import com.geekydroid.passcrypt.listeners.GenericOnClickListener
import com.geekydroid.passcrypt.viewmodels.SettingsFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings), GenericOnClickListener<Int> {

    @Inject
    lateinit var application: PasscryptApp
    private lateinit var fragmentView: View
    private lateinit var selfDestructionSwitch: SwitchMaterial
    private lateinit var tvNumberOfAttempts: TextView
    private lateinit var tvChangeMasterPassword: TextView
    private lateinit var tvSelfDestruction: TextView
    private lateinit var tvRatePasscrypt: TextView
    private lateinit var tvSharePasscrypt: TextView
    private lateinit var tvContactSupport: TextView
    private lateinit var tvOtherApps: TextView
    private lateinit var ivEditSelfDestructionCount: ImageView
    private lateinit var exportDataToCSV: TextView
    private val viewModel: SettingsFragmentViewModel by viewModels()
    private lateinit var userSettings: User
    private var flagChanged = false
    private var selfDestructionCount = 0
    private var activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data!!.data

                viewModel.exportData(application, uri)

            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentView = view
        setUI()

        viewModel.getUserSetting().observe(viewLifecycleOwner) { data ->
            userSettings = data
            selfDestructionCount = userSettings.selfDestructiveCount
            updateUI()
        }

        tvSelfDestruction.setOnClickListener {
            updateSelfDestructionSwitch()
        }

        selfDestructionSwitch.setOnCheckedChangeListener { _, _ ->
            updateSelfDestructionSwitch()
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

        ivEditSelfDestructionCount.setOnClickListener {
            if (userSettings.selfDestructive) {
                showUpdateDialog()
            } else {
                "Self Destruction option is not enabled".showSnackBar()
            }
        }

        exportDataToCSV.setOnClickListener {
            subscribeToExportResult()
            openFilePicker()
        }

    }

    private fun subscribeToExportResult() {
        viewModel.exportSucess.observe(viewLifecycleOwner) { result ->
            if (result == Result.SUCCESS) {
                showToast("The details are exported to a Excel file successfully")
            } else {
                showToast("Cannot Export data to Excel. Please Try again")
            }
        }
    }



    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/vnd.ms-excel"
            putExtra(
                Intent.EXTRA_TITLE,
                "Passcrypt ${HashingUtils.getCurrentTimeInMs()}"
            )
        }
        activityResultLauncher.launch(intent)
    }

    private fun String.showSnackBar() {
        Snackbar.make(fragmentView, this, Snackbar.LENGTH_SHORT).show()
    }

    private fun showUpdateDialog() {
        val dialog = UpdateSelfDestructionCountDialog(selfDestructionCount, this)
        val fm = requireActivity().supportFragmentManager
        dialog.show(fm, "update_self_destruction_count")
    }

    private fun updateSelfDestructionSwitch() {
        if (flagChanged) {
            userSettings.selfDestructive = !userSettings.selfDestructive
            viewModel.updateUserSettings(userSettings)
        }
        flagChanged = !flagChanged
    }

    private fun updateUI() {
        selfDestructionSwitch.isChecked = userSettings.selfDestructive
        flagChanged = true
        tvNumberOfAttempts.text =
            getString(R.string.number_of_attempts, userSettings.selfDestructiveCount.toString())
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
        tvSelfDestruction = fragmentView.findViewById(R.id.tv_self_destruction)
        selfDestructionSwitch = fragmentView.findViewById(R.id.switch_self_destruction)
        tvNumberOfAttempts = fragmentView.findViewById(R.id.tv_number_of_attempts)
        tvChangeMasterPassword = fragmentView.findViewById(R.id.tv_change_master_password)
        tvRatePasscrypt = fragmentView.findViewById(R.id.tv_rate_passcrypt)
        tvSharePasscrypt = fragmentView.findViewById(R.id.tv_share_passcrypt)
        tvContactSupport = fragmentView.findViewById(R.id.tv_contact_support)
        tvOtherApps = fragmentView.findViewById(R.id.tv_other_products)
        exportDataToCSV = fragmentView.findViewById(R.id.tv_export_csv)
        ivEditSelfDestructionCount = fragmentView.findViewById(R.id.iv_edit_self_destruction_count)
    }

    override fun onClick(value: Int) {
        selfDestructionCount = value
        userSettings.selfDestructiveCount = selfDestructionCount
        updateSettings()
    }

    private fun updateSettings() {
        viewModel.updateUserSettings(userSettings)
    }
}