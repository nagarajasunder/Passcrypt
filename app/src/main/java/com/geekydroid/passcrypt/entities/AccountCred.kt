package com.geekydroid.passcrypt.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(tableName = "ACCOUNT_CRED")
data class AccountCred(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CRED_ID")
    val credId: Int = 0,
    @ColumnInfo(name = "TITLE")
    var title: String,
    @ColumnInfo(name = "SITE_NAME")
    var siteName: String,
    @ColumnInfo(name = "USER_NAME")
    var userName: String,
    @ColumnInfo(name = "PASSWORD")
    var password: String,
    @ColumnInfo(name = "COMMENTS")
    var comments: String,
    @ColumnInfo(name = "IS_FAVORITE")
    var isFavourite: Boolean = false,
    @ColumnInfo(name = "CREATED_ON")
    val createdOn: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "UPDATED_ON")
    val updatedOn: Long
) {

    val createdOnFormatted: String
        get() = DateFormat.getDateTimeInstance().format(createdOn)

    val updatedOnFormatted: String
        get() = DateFormat.getDateTimeInstance().format(updatedOn)
}