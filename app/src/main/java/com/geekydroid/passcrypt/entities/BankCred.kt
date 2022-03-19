package com.geekydroid.passcrypt.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(tableName = "BANK_CRED")
data class BankCred(
    @PrimaryKey(autoGenerate = true)
    val credId: Int = 0,
    var bankName: String = "",
    var accountNumber: String = "",
    var ifscCode: String = "",
    var customerId: String = "",
    var comments: String = "",
    var createdOn: Long = System.currentTimeMillis(),
    var updatedOn: Long
) {
    val updatedOnFormatted: String
        get() = DateFormat.getDateTimeInstance().format(updatedOn)
}