package com.geekydroid.passcrypt.Utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.geekydroid.passcrypt.R

class DialogBuilder(
    private val context: Context,
    private val view: Int,
    private val waitingMessage: String,
    private val viewGroup: ViewGroup
) {


    fun getDialog(): AlertDialog {
        val view = LayoutInflater.from(context).inflate(view, viewGroup, false)
        val warningText = view.findViewById<TextView>(R.id.tv_waiting_text)

        warningText.text = waitingMessage

        return AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(false)
            .create()
    }


}