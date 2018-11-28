package com.example.android.kotlin.implicitintents

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.support.v4.app.ShareCompat



class MainActivity : AppCompatActivity() {
    private val mWebsiteEdit by lazy { findViewById<EditText>(R.id.website_edittext)}
    private val mLocationEdit by lazy {findViewById<EditText>(R.id.location_edittext)}
    private val mShareEdit by lazy {findViewById<EditText>(R.id.share_edittext)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openWebsite(view: View) {
        val url = mWebsiteEdit.text.toString()
        val webPage = Uri.parse(url)
        val intent = Intent(ACTION_VIEW, webPage)
        if(intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!")
        }
    }

    fun openLocation(view: View) {
        val location = mLocationEdit.text.toString()
        val addressUri = Uri.parse("geo:0,0?q=" + location)
        val intent = Intent(ACTION_VIEW, addressUri)
        if(intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!")
        }
    }

    fun shareText(view: View) {
        val txt = mShareEdit.text.toString()
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(R.string.share_text_with)
                .setText(txt)
                .startChooser()
    }
}
