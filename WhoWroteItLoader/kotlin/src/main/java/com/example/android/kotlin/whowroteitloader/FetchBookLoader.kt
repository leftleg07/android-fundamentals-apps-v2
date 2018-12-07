package com.example.android.kotlin.whowroteitloader

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

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

private fun fetchBook(queryString: String?): Book? {
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

class FetchBookLoader(context: Context, private val queryString: String): AsyncTaskLoader<Book>(context) {
    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    override fun loadInBackground() = fetchBook(queryString)
}