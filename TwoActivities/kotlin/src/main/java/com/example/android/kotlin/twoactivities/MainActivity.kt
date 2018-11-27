package com.example.android.kotlin.twoactivities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.android.kotlin.twoactivities.SecondActivity.Companion.EXTRA_REPLY

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MESSAGE = "com.example.android.kotlin.twoactivities.extra.MESSAGE"
        const val TEXT_REQUEST = 1
    }

    private val mMessageEdit by lazy { findViewById<EditText>(R.id.editText_main) }
    private val mReplayHeaderView by lazy { findViewById<View>(R.id.text_header_reply) }
    private val mReplayText by lazy { findViewById<TextView>(R.id.text_message_reply) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun launchSecondActivity(view: View) {
        val intent = Intent(this, SecondActivity::class.java)
        val message = mMessageEdit.text.toString()
        intent.putExtra(EXTRA_MESSAGE, message)
        startActivityForResult(intent, TEXT_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == TEXT_REQUEST) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    mReplayHeaderView.visibility = View.VISIBLE
                    mReplayText.visibility = View.VISIBLE
                    mMessageEdit.text.clear()
                    val replay = data?.getStringExtra(EXTRA_REPLY) ?: ""
                    mReplayText.text = replay
                }
                else -> {
                    mReplayHeaderView.visibility = View.VISIBLE
                    mReplayText.visibility = View.INVISIBLE
                }
            }
        }
    }
}
