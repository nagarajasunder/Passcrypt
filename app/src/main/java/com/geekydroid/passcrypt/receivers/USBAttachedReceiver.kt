package com.geekydroid.passcrypt.receivers


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.geekydroid.passcrypt.ui.MainActivity


class USBAttachedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(
            context,
            "USB Device attached ${intent?.extras?.getBoolean("connected")}",
            Toast.LENGTH_LONG
        ).show()
        showWarning(context!!)
    }

    private fun showWarning(context: Context) {
        val dialog = AlertDialog.Builder(context)
            .setTitle("USB Connection")
            .setMessage("You cannot connect to USB when Passcrypt is running")
            .setPositiveButton(
                "Quit App"
            ) { dialog, _ ->
                quitApp(context)
                dialog.dismiss()
            }
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun quitApp(context: Context) {
        val closeIntent = Intent(context, MainActivity::class.java)
        closeIntent.putExtra("QUIT", true)
        context.startActivity(closeIntent)
    }

}