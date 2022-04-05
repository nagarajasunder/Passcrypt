package com.geekydroid.passcrypt.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "USER")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    var masterPassHash: String,
    val isMasterPassSet: Boolean,
    var selfDestructive: Boolean = false,
    var selfDestructiveCount: Int = 3,
    val createdOn: Long = System.currentTimeMillis(),
    var updatedOn: Long
)