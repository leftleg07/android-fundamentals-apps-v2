package com.example.android.kotlin.hellosharedprefs

import android.content.Context
import android.support.v4.content.ContextCompat
import io.reactivex.subjects.PublishSubject

class MyPrefs private constructor(context: Context) {
    companion object {
        // Key for current count
        private const val COUNT_KEY = "count"
        // Key for current color
        private const val COLOR_KEY = "color"

        // Name of shared preferences file
        private const val SHARED_PREF_FILE = "com.example.android.kotlin.hellosharedprefs"

        @Volatile
        private var INSTANCE: MyPrefs? = null

        fun getInstance(context: Context): MyPrefs =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildPrefs(context).also { INSTANCE = it }
                }

        private fun buildPrefs(context: Context) = MyPrefs(context)
    }

    // Shared preferences object
    private val mPreferences = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
    private val mColor = ContextCompat.getColor(context, R.color.default_background)


    private val _count = PublishSubject.create<Int>()
    var count
        get() = mPreferences.getInt(COUNT_KEY, 0)
        set(value) {
            _count.onNext(value)
            mPreferences.edit().putInt(COUNT_KEY, value).apply()
        }

    fun observeCount(perform: (Int) -> Unit) = _count.subscribe { perform(it) }


    private val _color = PublishSubject.create<Int>()
    var color
        get() = mPreferences.getInt(COLOR_KEY, mColor)
        set(value) {
            _color.onNext(value)
            mPreferences.edit().putInt(COLOR_KEY, value).apply()
        }

    fun observeColor(perform: (Int) -> Unit) = _color.subscribe { perform(it) }


    fun reset() {
        mPreferences.edit().clear().apply()
        _count.onNext(count)
        _color.onNext(color)
    }

}