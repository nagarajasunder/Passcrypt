package com.geekydroid.passcrypt.entities

data class PasswordGeneratorConfig(
    var capitalLetters: Boolean = true,
    var smallLetters: Boolean = false,
    var numbers: Boolean = false,
    var specialCharacters: Boolean = false,
    var passwordSize: Int = 25
)