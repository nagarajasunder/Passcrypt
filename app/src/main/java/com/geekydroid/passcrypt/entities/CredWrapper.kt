package com.geekydroid.passcrypt.entities

import java.text.DateFormat

data class CredWrapper(
    val credId: Int,
    val credTitle: String,
    val credComments: String,
    val credType: String,
    val isFavorite: Boolean,
    val createdOn: Long
) {
    val createdOnFormatted
        get() = DateFormat.getDateTimeInstance().format(createdOn)
}