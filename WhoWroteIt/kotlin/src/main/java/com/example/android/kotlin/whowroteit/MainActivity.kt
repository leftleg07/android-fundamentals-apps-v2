package com.example.android.kotlin.whowroteit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val mQueryEdit by lazy { findViewById<EditText>(R.id.bookInput) }
    private val mTitleText by lazy { findViewById<TextView>(R.id.titleText) }
    private val mAuthorText by lazy { findViewById<TextView>(R.id.authorText) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun searchBooks(view: View) {
        val queryString = mQueryEdit.text.toString()
        // Hide the keyboard when the button is pushed.
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputManager?.hideSoftInputFromWindow(view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS)

        // Check the status of the network connection.
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val networkInfo = connMgr?.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected
                && queryString.isNotEmpty()) {
            FetchBookAsynTask(mTitleText, mAuthorText).execute(queryString)
            mAuthorText.text = ""
            mTitleText.setText(R.string.loading)
            // Otherwise update the TextView to tell the user there is no
            // connection, or no search term.
        } else {
            if (queryString.isEmpty()) {
                mAuthorText.text = ""
                mTitleText.setText(R.string.no_search_term)
            } else {
                mAuthorText.text = ""
                mTitleText.setText(R.string.no_network)
            }
        }


    }
}
