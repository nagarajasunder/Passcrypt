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
    private var generatedPassword = ""

    private lateinit var ivRefresh: ImageView
    private lateinit var ivCopy: ImageView

    private var currentPasswordConfig = PasswordGeneratorConfig()

    private val ViewModel: PasswordGeneratorViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentView = view
        setUI()


        ViewModel.generatedPassword.observe(viewLifecycleOwner) {
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
            ViewModel.passwordConfig.value = currentPasswordConfig
        }

        switchSmallLetters.setOnCheckedChangeListener { compoundButton, b ->
            currentPasswordConfig.smallLetters = b
            ViewModel.passwordConfig.value = currentPasswordConfig
        }

        switchCaptialLetters.setOnCheckedChangeListener { compoundButton, b ->
            currentPasswordConfig.capitalLetters = b
            ViewModel.passwordConfig.value = currentPasswordConfig
        }

        switchSpecialCharacters.setOnCheckedChangeListener { compoundButton, b ->
            currentPasswordConfig.specialCharacters = b
            ViewModel.passwordConfig.value = currentPasswordConfig
        }

        switchNumbers.setOnCheckedChangeListener { compoundButton, b ->
            currentPasswordConfig.numbers = b
            ViewModel.passwordConfig.value = currentPasswordConfig
        }

        ivRefresh.setOnClickListener {
            ViewModel.passwordConfig.value = currentPasswordConfig
        }

        ivCopy.setOnClickListener {
            copyPasswordToClipBoard()
        }

    }

    private fun copyPasswordToClipBoard() {
        val manager = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("GENERATED_PASS", generatedPassword)
        manager.setPrimaryClip(clipData)
        showSnackBar("Password copied to clipboard")
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(fragmentView, message, Snackbar.LENGTH_SHORT).show()
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

        ivRefresh = fragmentView.findViewById(R.id.iv_refresh)
        ivCopy = fragmentView.findViewById(R.id.iv_copy)

        tvPasswordLengthLabel.text =
            getString(R.string.password_length, "25")
        lengthSlider.setLabelFormatter {
            it.toInt().toString()
        }
    }
}