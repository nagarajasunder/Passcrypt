package com.geekydroid.passcrypt.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(
    tableName = "CARD",
    foreignKeys = [ForeignKey(
        entity = BankCred::class,
        parentColumns = ["credId"],
        childColumns = ["bankId"],
        onUpdate = CASCADE,
        onDelete = CASCADE
    )]
)
data class Card(
    @PrimaryKey(autoGenerate = true)
    var cardId: Int = 0,
    var cardNumber: String = "",
    var cvv: String = "",
    var cardPinNumber: String = "",
    var cardExpiryDate: String = "",
    @ColumnInfo(index = true)
    var bankId: Int,
    val createdOn: Long = System.currentTimeMillis(),
    var updatedOn: Long
) {
    val createdOnFormatted: String
        get() = DateFormat.getDateTimeInstance().format(createdOn)
}