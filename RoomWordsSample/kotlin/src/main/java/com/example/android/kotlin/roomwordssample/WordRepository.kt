package com.example.android.kotlin.roomwordssample

import android.content.Context
import androidx.annotation.VisibleForTesting
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

@OpenForTesting
class WordRepository private constructor(private val wordDao: WordDao) {

    companion object {
        @Volatile
        private var INSTANCE: WordRepository? = null

        fun getInstance(context: Context) = INSTANCE
                ?: synchronized(this) {
                    val dao = WordRoomDatabase.getDatabase(context).wordDao()
                    buildRepository(dao).also { INSTANCE = it } }

        /**
         * using only test case
         */
        @VisibleForTesting(otherwise = VisibleForTesting.NONE)
        fun getInstance(dao: WordDao) = INSTANCE
                ?: synchronized(this) { buildRepository(dao).also { INSTANCE = it } }

        private fun buildRepository(dao: WordDao) = WordRepository(dao)

    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allWords = wordDao.getAlphabetizedWords()

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    fun insert(word: Word) {
        Completable.fromAction { wordDao.insert(word) }
                .subscribeOn(Schedulers.io())
                .subscribe {}

    }

}
