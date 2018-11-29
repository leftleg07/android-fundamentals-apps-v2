package com.example.android.kotlin.dialogforalert

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onClickShowAlert(view: View) {
        AlertDialog.Builder(this)
                .setTitle(R.string.alert_title)
                .setMessage(R.string.alert_message)
                .setPositiveButton(R.string.ok_button) { dialog, which -> displayToast(getString(R.string.pressed_ok))}
                .setNegativeButton(R.string.cancel_button) { dialog, which -> displayToast(getString(R.string.pressed_cancel))}
                .show()
    }

    private inline fun displayToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
