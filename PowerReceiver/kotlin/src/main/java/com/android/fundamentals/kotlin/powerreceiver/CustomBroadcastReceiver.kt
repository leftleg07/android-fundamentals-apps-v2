package com.android.fundamentals.kotlin.powerreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CustomBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val intentAction = intent?.action

        intentAction?.let {
            val toastMessage = context?.getString(
                    when (it) {
                        Intent.ACTION_POWER_CONNECTED -> R.string.power_connected
                        Intent.ACTION_POWER_DISCONNECTED -> R.string.power_disconnected
                        else -> R.string.unknown_action
                    }
            )

            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }
}