package com.geekydroid.passcrypt.entities

import java.text.DateFormat

data class CredWrapper(
    val credId: Int,
    val credName: String,
    val credComments: String,
    val credType: String,
    val createdOn: Long
) {
    val createdOnFormatted
        get() = DateFormat.getDateTimeInstance().format(createdOn)
}