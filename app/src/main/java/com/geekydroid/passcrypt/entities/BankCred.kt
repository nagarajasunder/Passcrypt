package com.geekydroid.passcrypt.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BANK_CRED")
data class BankCred(
    @PrimaryKey(autoGenerate = true)
    val credId: Int = 0,
    var bankName: String,
    var cardNumber: String,
    var cvv: String = "",
    var accountNumber: String = "",
    var ifscCode: String,
    var customerId: String = "",
    var createdOn: Long = System.currentTimeMillis(),
    var updatedOn: Long
)