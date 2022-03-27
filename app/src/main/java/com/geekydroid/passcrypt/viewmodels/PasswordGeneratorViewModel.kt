package com.geekydroid.passcrypt.viewmodels

import androidx.lifecycle.*
import com.geekydroid.passcrypt.entities.PasswordGeneratorConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import java.security.SecureRandom
import javax.inject.Inject

@HiltViewModel
class PasswordGeneratorViewModel @Inject constructor() : ViewModel() {

    private val capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val smallLetters = "abcdefghijklmnopqrstuvwxyz"
    private val numbers = "0123456789"
    private val specialCharacters = " !\"#$%&'()*+,-./:;<=>?@[]^_`{|}~"

    val passwordConfig: MutableLiveData<PasswordGeneratorConfig> = MutableLiveData(
        PasswordGeneratorConfig()
    )

    val generatedPassword: LiveData<String> = Transformations.switchMap(passwordConfig) {
        getPassword(it)
    }


    fun getPassword(config: PasswordGeneratorConfig): LiveData<String> {
        return liveData {
            val password = constructPassword(config)
            emit(password)
        }
    }

    fun constructPassword(config: PasswordGeneratorConfig): String {
        val FINAL_SALT = StringBuilder()
        FINAL_SALT.append(
            if (config.capitalLetters)
                capitalLetters
            else
                ""
        )
        FINAL_SALT.append(
            if (config.smallLetters)
                smallLetters
            else
                ""
        )
        FINAL_SALT.append(
            if (config.numbers)
                numbers
            else
                ""
        )
        FINAL_SALT.append(
            if (config.specialCharacters)
                specialCharacters
            else
                ""
        )
        if (FINAL_SALT.isNotEmpty()) {
            var generatedPassword = ""
            val secureRandom = SecureRandom()
            while (generatedPassword.length < config.passwordSize) {
                val randomIndex = (secureRandom.nextFloat() * FINAL_SALT.length).toInt()
                generatedPassword += (FINAL_SALT.toString()[randomIndex])
            }
            return generatedPassword
        } else {
            return ""
        }
    }


}