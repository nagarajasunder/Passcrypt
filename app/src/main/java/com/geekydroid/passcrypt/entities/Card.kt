package com.geekydroid.passcrypt.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(
    tableName = "CARD",
    foreignKeys = [ForeignKey(
        entity = BankCred::class,
        parentColumns = ["credId"],
        childColumns = ["bankId"]
    )]
)
data class Card(
    @PrimaryKey(autoGenerate = true)
    var cardId: Int = 0,
    var cardNumber: String = "",
    var cvv: String = "",
    var cardPinNumber: String = "",
    var cardExpiryDate: String = "",
    val bankId: Int,
    val createdOn: Long = System.currentTimeMillis(),
    var updatedOn: Long
) {
    val createdOnFormatted: String
        get() = DateFormat.getDateTimeInstance().format(createdOn)
}