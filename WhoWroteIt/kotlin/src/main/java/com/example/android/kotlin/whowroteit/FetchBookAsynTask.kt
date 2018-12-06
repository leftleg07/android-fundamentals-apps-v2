package com.example.android.kotlin.whowroteit

import android.os.AsyncTask
import android.widget.TextView
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

data class Book(val title: String, val author: String)

// Constants for the various components of the Books API request.
val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

val okHttpClient = OkHttpClient.Builder()
        .readTimeout(1000, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build();

var retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(okHttpClient)
        .build()

var service = retrofit.create<BookApiService>(BookApiService::class.java)

fun fetchBook(queryString: String?): Book? {
    if (queryString != null) {
        val books = service.fetchBooks(queryString)
        val body = books.execute().body()
        val asMap = Gson().fromJson(body, Map::class.java)
        val items = asMap?.get("items") as? ArrayList<Map<String, Any>>
        val item = items?.firstOrNull { it["volumeInfo"] != null }
        item?.let { item ->
            val volumeInfo = item["volumeInfo"] as? Map<String, Any>
            volumeInfo?.let {
                val title = it["title"] as? String
                val authors = it["authors"] as? List<String>
                if (title != null && authors != null) {
                    return Book(title, authors[0])
                }
            }
        }
    }
    return null
}


class FetchBookAsynTask(title: TextView, author: TextView) : AsyncTask<String, Void, Book?>() {
    val mTitleText = WeakReference<TextView>(title)
    val mAuthorText = WeakReference<TextView>(author)

    override fun doInBackground(vararg params: String?): Book? {
        val query = params?.get(0)
        return fetchBook(query)
    }

    override fun onPostExecute(result: Book?) {
        super.onPostExecute(result)
        if (result != null) {
            mTitleText.get()!!.text = result.title
            mAuthorText.get()!!.text = result.author
        } else {
            // If none are found, update the UI to show failed results.
            mTitleText.get()!!.setText(R.string.no_results)
            mAuthorText.get()!!.text = ""
        }
    }


}