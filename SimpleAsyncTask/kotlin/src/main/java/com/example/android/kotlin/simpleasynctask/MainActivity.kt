package com.example.android.kotlin.simpleasynctask

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    companion object {
        private const val BUNDLE_CONTENT_TEXT = "_bundle_content_text"
    }

    private val mContentText by lazy { findViewById<TextView>(R.id.textView1) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?.apply { mContentText.text = getString(BUNDLE_CONTENT_TEXT) }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString(BUNDLE_CONTENT_TEXT, mContentText.text.toString())
    }

    fun startTask(view: View) {
        SimpleAsyncTask(mContentText).execute()
    }
}
