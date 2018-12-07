package com.example.android.kotlin.whowroteitloader

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface BookApiService {
    @GET("/books/v1/volumes")
    fun fetchBooks(@Query("q") query: String, @Query("maxResults") maxResults: Int = 10, @Query("printType") printType: String = "books"): Call<String>
}