package com.example.android.kotlin.hellosharedprefs

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {

    private val mShowCountTextView by lazy { findViewById<TextView>(R.id.count_textview) }
    private val mPrefs by lazy { MyPrefs.getInstance(this) }
    private lateinit var mCountDispose: Disposable
    private lateinit var mColorDispose: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mCountDispose =  mPrefs.observeCount { mShowCountTextView.text = String.format("%s", it) }
        mColorDispose = mPrefs.observeColor { mShowCountTextView.setBackgroundColor(it) }

        mShowCountTextView.text = String.format("%s", mPrefs.count)
        mShowCountTextView.setBackgroundColor(mPrefs.color)
    }

    override fun onDestroy() {
        if(!mCountDispose.isDisposed) mCountDispose.dispose()
        if(!mColorDispose.isDisposed) mColorDispose.dispose()
        super.onDestroy()
    }

    fun changeBackground(view: View) {
        val color = (view.background as ColorDrawable).color
        mPrefs.color = color
    }

    fun countUp(view: View) {
        mPrefs.count++
    }

    fun reset(view: View) {
        mPrefs.reset()
    }
}
