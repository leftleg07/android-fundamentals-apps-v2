package com.example.android.kotlin.whowroteitloader

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val mQueryEdit by lazy { findViewById<EditText>(R.id.bookInput) }
    private val mTitleText by lazy { findViewById<TextView>(R.id.titleText) }
    private val mAuthorText by lazy { findViewById<TextView>(R.id.authorText) }

    private val mCallBack = object : LoaderManager.LoaderCallbacks<Book> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Book> {
            val queryString = args?.getString("queryString") ?: ""
            return FetchBookLoader(this@MainActivity, queryString)
        }

        override fun onLoadFinished(loader: Loader<Book>, data: Book?) {
            if (data != null) {
                mTitleText.text = data.title
                mAuthorText.text = data.author
            } else {
                // If none are found, update the UI to show failed results.
                mTitleText.setText(R.string.no_results)
                mAuthorText.text = ""
            }
        }

        override fun onLoaderReset(loader: Loader<Book>) {
            // Do nothing.
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportLoaderManager.getLoader<Book>(0) != null) {
            supportLoaderManager.initLoader(0, null, mCallBack)
        }
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
            val queryBundle = Bundle()
            queryBundle.putString("queryString", queryString)
            supportLoaderManager.restartLoader(0, queryBundle, mCallBack)
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
