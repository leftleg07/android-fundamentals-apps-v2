package com.example.android.kotlin.simpleasynctask

import android.os.AsyncTask
import android.widget.TextView
import java.lang.ref.WeakReference
import java.util.*

class SimpleAsyncTask(tv: TextView) : AsyncTask<Void, Void, String>() {
    private val mContentText= WeakReference(tv)

    override fun doInBackground(vararg params: Void?): String {
        val s = Random().nextInt(11) * 200
        Thread.sleep(s.toLong())
        // Return a String result.
        return "Awake at last after sleeping for $s milliseconds!"
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        mContentText.get()?.apply { text = result }
    }
}