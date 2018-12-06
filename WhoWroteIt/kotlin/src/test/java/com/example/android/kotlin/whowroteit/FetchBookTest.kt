package com.example.android.kotlin.whowroteit

import com.google.common.truth.Truth
import org.junit.Test

class FetchBookTest {
    @Test
    fun fetch_book_query() {
        val book = fetchBook("Romeo and Juliet")
        Truth.assertThat(book).isNotNull()
    }

    @Test
    fun fetch_book_empty_query() {
        val book = fetchBook("")
        Truth.assertThat(book).isNull()
    }

    @Test
    fun fetch_book_null_query() {
        val book = fetchBook(null)
        Truth.assertThat(book).isNull()
    }
}