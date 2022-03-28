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


    private fun getPassword(config: PasswordGeneratorConfig): LiveData<String> {
        return liveData {
            val password = constructPassword(config)
            emit(password)
        }
    }

    private fun constructPassword(config: PasswordGeneratorConfig): String {
        val finalSalt = StringBuilder()
        finalSalt.append(
            if (config.capitalLetters)
                capitalLetters
            else
                ""
        )
        finalSalt.append(
            if (config.smallLetters)
                smallLetters
            else
                ""
        )
        finalSalt.append(
            if (config.numbers)
                numbers
            else
                ""
        )
        finalSalt.append(
            if (config.specialCharacters)
                specialCharacters
            else
                ""
        )
        return if (finalSalt.isNotEmpty()) {
            if (config.removeDuplicates && finalSalt.length < config.passwordSize) {
                ""
            } else {
                var generatedPassword = ""
                val passwordList = mutableListOf<Char>()
                val secureRandom = SecureRandom()
                while (generatedPassword.length < config.passwordSize) {
                    val randomIndex = (secureRandom.nextFloat() * finalSalt.length).toInt()
                    if (config.removeDuplicates) {
                        if (!passwordList.contains(finalSalt.toString()[randomIndex])) {
                            passwordList.add(finalSalt.toString()[randomIndex])
                            generatedPassword += (finalSalt.toString()[randomIndex])
                        }
                    } else {
                        generatedPassword += (finalSalt.toString()[randomIndex])
                    }
                }
                generatedPassword
            }
        } else {
            ""
        }
    }


}