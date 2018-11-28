package com.example.android.kotlin.hellocompat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val BUNDLE_COLOR_KEY = "_color"
    }


    // Array of color names; these match the color resources in color.xml.
    private val mColorArray = arrayOf("red", "pink", "purple", "deep_purple", "indigo", "blue", "light_blue", "cyan", "teal", "green", "light_green", "lime", "yellow", "amber", "orange", "deep_orange", "brown", "grey", "blue_grey", "black")

    private val mHelloText by lazy { findViewById<TextView>(R.id.hello_textview) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?.apply { val color = getInt(BUNDLE_COLOR_KEY); mHelloText.setTextColor(color) }
    }


    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.apply { putInt(BUNDLE_COLOR_KEY, mHelloText.currentTextColor) }
    }

    fun changeColor(view: View) {
        // Get a random color name from the color array (20 colors).
        val random = Random()
        val colorName = mColorArray[random.nextInt(20)]

        // Get the color identifier that matches the color name.
        val colorResourceName = resources.getIdentifier(colorName,
                "color", applicationContext.packageName)

        // Get the color ID from the resources the compatible way.
        val colorRes = ContextCompat.getColor(this, colorResourceName)
        mHelloText.setTextColor(colorRes)

    }
}
