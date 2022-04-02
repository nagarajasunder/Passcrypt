package com.geekydroid.passcrypt.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(tableName = "BANK_CRED")
data class BankCred(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "BANK_CRED_ID")
    val credId: Int = 0,
    @ColumnInfo(name = "BANK_NAME")
    var bankName: String = "",
    @ColumnInfo(name = "ACCOUNT_NUMBER")
    var accountNumber: String = "",
    @ColumnInfo(name = "IFSC_CODE")
    var ifscCode: String = "",
    @ColumnInfo(name = "CUSTOMER_ID")
    var customerId: String = "",
    @ColumnInfo(name = "COMMENTS")
    var comments: String = "",
    @ColumnInfo(name = "IS_FAVORITE")
    var isFavorite: Boolean = false,
    @ColumnInfo(name = "CREATED_ON")
    var createdOn: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "UPDATED_ON")
    var updatedOn: Long
) {
    val updatedOnFormatted: String
        get() = DateFormat.getDateTimeInstance().format(updatedOn)
}