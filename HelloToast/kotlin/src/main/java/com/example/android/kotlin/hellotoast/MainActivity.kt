package com.example.android.kotlin.hellotoast

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var mCount = 0
    private val mShowCountView by lazy { findViewById<TextView>(R.id.show_count) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mShowCountView.text = mCount.toString()
    }

    fun countUp(view: View) {
        mCount++
        mShowCountView.text = mCount.toString()
    }

    fun showToast(view: View) {
        Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT).show()
    }
}
