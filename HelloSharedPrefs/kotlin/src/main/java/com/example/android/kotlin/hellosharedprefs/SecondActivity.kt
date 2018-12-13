package com.example.android.kotlin.hellosharedprefs

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.disposables.Disposable

class SecondActivity : AppCompatActivity() {

    private val mPrefs by lazy { MyPrefs.getInstance(this) }
    private lateinit var mCountDispose: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCountDispose =  mPrefs.observeCount {
            Log.i("aaa", it.toString())
        }
        mPrefs.count++

    }

    override fun onDestroy() {
        if(!mCountDispose.isDisposed) mCountDispose.dispose()
        super.onDestroy()
    }
}
