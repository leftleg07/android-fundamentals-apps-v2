package com.example.android.kotlin.hellosharedprefs

import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val mShowCountTextView by lazy { findViewById<TextView>(R.id.count_textview) }
    private val mPrefs by lazy { MyPrefs(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mShowCountTextView.text = String.format("%s", mPrefs.count)
        mShowCountTextView.setBackgroundColor(mPrefs.color)

    }

    fun changeBackground(view: View) {
        val color = (view.background as ColorDrawable).color
        mShowCountTextView.setBackgroundColor(color)
        mPrefs.color = color
    }

    fun countUp(view: View) {
        mPrefs.count++
        mShowCountTextView.text = String.format("%s", mPrefs.count)
    }

    fun reset(view: View) {
        mPrefs.reset()
        mShowCountTextView.text = String.format("%s", mPrefs.count)
        mShowCountTextView.setBackgroundColor(mPrefs.color)
    }
}
