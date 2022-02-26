package com.geekydroid.passcrypt.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "USER")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val masterPassHash: String,
    val isMasterPassSet: Boolean,
    val createdOn: Long = System.currentTimeMillis()
)