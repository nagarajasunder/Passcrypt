package com.geekydroid.passcrypt.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CREDENTIALS")
data class Credentials(
    @PrimaryKey(autoGenerate = true)
    val credId: Int = 0,
    var siteName: String,
    var userName: String,
    var password: String
)