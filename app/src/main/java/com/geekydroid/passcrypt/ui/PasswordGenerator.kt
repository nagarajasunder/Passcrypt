package com.geekydroid.passcrypt.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.entities.PasswordGeneratorConfig
import com.geekydroid.passcrypt.viewmodels.PasswordGeneratorViewModel
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PasswordGenerator : Fragment(R.layout.fragment_password_generator) {

    private lateinit var fragmentView: View

    private lateinit var tvPasswordLengthLabel: TextView
    private lateinit var tvGeneratedPassword: TextView
    private lateinit var lengthSlider: Slider
    private lateinit var switchCaptialLetters: SwitchMaterial
    private lateinit var switchSmallLetters: SwitchMaterial
    private lateinit var switchSpecialCharacters: SwitchMaterial
    private lateinit var switchNumbers: SwitchMaterial
    private lateinit var switchNoDuplicates: SwitchMaterial
    private var generatedPassword = ""

    private lateinit var ivRefresh: ImageView
    private lateinit var ivCopy: ImageView

    private var currentPasswordConfig = PasswordGeneratorConfig()

    private val viewModel: PasswordGeneratorViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentView = view
        setUI()


        viewModel.getGeneratedPass().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                generatedPassword = it
                setPassword(it)
            } else {
                generatedPassword = ""
                showWarning()
            }
        }

        lengthSlider.addOnChangeListener { _, value, _ ->
            tvPasswordLengthLabel.text =
                getString(R.string.password_length, value.toInt().toString())
            currentPasswordConfig.passwordSize = value.toInt()
            viewModel.passwordConfig.value = currentPasswordConfig
        }

        switchSmallLetters.setOnCheckedChangeListener { _, b ->
            currentPasswordConfig.smallLetters = b
            viewModel.passwordConfig.value = currentPasswordConfig
        }

        switchCaptialLetters.setOnCheckedChangeListener { _, b ->
            currentPasswordConfig.capitalLetters = b
            viewModel.passwordConfig.value = currentPasswordConfig
        }

        switchSpecialCharacters.setOnCheckedChangeListener { _, b ->
            currentPasswordConfig.specialCharacters = b
            viewModel.passwordConfig.value = currentPasswordConfig
        }

        switchNumbers.setOnCheckedChangeListener { _, b ->
            currentPasswordConfig.numbers = b
            viewModel.passwordConfig.value = currentPasswordConfig
        }

        switchNoDuplicates.setOnCheckedChangeListener { _, b ->

            currentPasswordConfig.removeDuplicates = b
            viewModel.passwordConfig.value = currentPasswordConfig

        }

        ivRefresh.setOnClickListener {
            viewModel.passwordConfig.value = currentPasswordConfig
        }

        ivCopy.setOnClickListener {
            copyPasswordToClipBoard()
        }

    }

    private fun copyPasswordToClipBoard() {
        val manager = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("GENERATED_PASS", generatedPassword)
        manager.setPrimaryClip(clipData)
        "Password copied to clipboard".showSnackBar()
    }

    private fun String.showSnackBar() {
        Snackbar.make(fragmentView, this, Snackbar.LENGTH_SHORT).show()
    }

    private fun showWarning() {
        tvGeneratedPassword.text = getString(R.string.password_generator_warning)
        ivCopy.isEnabled = false
        ivRefresh.isEnabled = false
    }

    private fun setPassword(password: String) {
        tvGeneratedPassword.text = password
        ivCopy.isEnabled = true
        ivRefresh.isEnabled = true
    }

    private fun setUI() {

        tvPasswordLengthLabel = fragmentView.findViewById(R.id.tv_password_length)
        tvGeneratedPassword = fragmentView.findViewById(R.id.tv_generated_password)
        lengthSlider = fragmentView.findViewById(R.id.slider_password_size)
        switchCaptialLetters = fragmentView.findViewById(R.id.switch_capital_letters)
        switchSmallLetters = fragmentView.findViewById(R.id.switch_small_letters)
        switchSpecialCharacters = fragmentView.findViewById(R.id.switch_special_characters)
        switchNumbers = fragmentView.findViewById(R.id.switch_numbers)
        switchNoDuplicates = fragmentView.findViewById(R.id.switch_no_duplicates)

        ivRefresh = fragmentView.findViewById(R.id.iv_refresh)
        ivCopy = fragmentView.findViewById(R.id.iv_copy)

        tvPasswordLengthLabel.text =
            getString(R.string.password_length, "25")
        lengthSlider.setLabelFormatter {
            it.toInt().toString()
        }
    }
}