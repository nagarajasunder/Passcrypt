package com.geekydroid.passcrypt.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ACCOUNT_CRED")
data class AccountCred(
    @PrimaryKey(autoGenerate = true)
    val credId: Int = 0,
    var siteName: String,
    var userName: String,
    var password: String,
    var comments: String,
    val createdOn: Long = System.currentTimeMillis(),
    val updatedOn: Long
)