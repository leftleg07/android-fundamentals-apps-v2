package com.example.android.kotlin.hellosharedprefs

import android.content.Context
import android.content.SharedPreferences
import android.support.v4.content.ContextCompat
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MyPrefs(context: Context) {
    companion object {
        // Key for current count
        const val COUNT_KEY = "count"
        // Key for current color
        const val COLOR_KEY = "color"

        // Name of shared preferences file
        private const val SHARED_PREF_FILE = "com.example.android.kotlin.hellosharedprefs"
    }

    // Shared preferences object
    val mPreferences = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
    private val mColor = ContextCompat.getColor(context, R.color.default_background)

    private val _count = PublishSubject.create<Int>()
    var count: Observable<Int>
        get() = mPreferences.getInt(COUNT_KEY, 0)
        set(value) = mPreferences.edit().putInt(COUNT_KEY, value.).apply()

    var color
        get() = mPreferences.getInt(COLOR_KEY, mColor)
        set(value) = mPreferences.edit().putInt(COLOR_KEY, value).apply()

    fun reset() = mPreferences.edit().clear().apply()

}