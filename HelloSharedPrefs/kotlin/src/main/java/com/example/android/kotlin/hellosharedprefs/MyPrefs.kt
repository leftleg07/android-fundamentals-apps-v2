package com.example.android.kotlin.hellosharedprefs

import android.content.Context
import android.content.SharedPreferences
import android.support.v4.content.ContextCompat

class MyPrefs(context: Context) {
    companion object {
        // Key for current count
        private const val COUNT_KEY = "count"
        // Key for current color
        private const val COLOR_KEY = "color"

        // Name of shared preferences file
        private const val SHARED_PREF_FILE = "com.example.android.kotlin.hellosharedprefs"
    }

    // Shared preferences object
    private val mPreferences = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
    private val mColor = ContextCompat.getColor(context, R.color.default_background)

    var count
        get() = mPreferences.getInt(COUNT_KEY, 0)
        set(value) = mPreferences.edit().putInt(COUNT_KEY, value).apply()

    var color
        get() = mPreferences.getInt(COLOR_KEY, mColor)
        set(value) = mPreferences.edit().putInt(COLOR_KEY, value).apply()

    fun reset() = mPreferences.edit().clear().apply()

}