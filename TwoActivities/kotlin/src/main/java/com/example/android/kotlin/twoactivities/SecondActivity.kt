package com.example.android.kotlin.twoactivities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.android.kotlin.twoactivities.MainActivity.Companion.EXTRA_MESSAGE

class SecondActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_REPLY = "com.example.android.kotlin.twoactivities.extra.REPLY"
    }

    private val mMessageText by lazy { findViewById<TextView>(R.id.text_message) }
    private val mReplayEdit by lazy { findViewById<EditText>(R.id.editText_second) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val message = intent.getStringExtra(EXTRA_MESSAGE)
        mMessageText.text = message

    }

    fun returnReply(view: View) {
        val replay = mReplayEdit.text.toString()
        val replayIntent = Intent()
        replayIntent.putExtra(EXTRA_REPLY, replay)
        setResult(Activity.RESULT_OK, replayIntent)
        finish()
    }
}
