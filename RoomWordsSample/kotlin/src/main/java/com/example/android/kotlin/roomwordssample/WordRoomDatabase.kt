package com.example.android.kotlin.roomwordssample

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

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context): WordRoomDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,
                WordRoomDatabase::class.java, "word_database.db")
                // Wipes and rebuilds instead of migrating if no Migration object.
                // Migration is not part of this codelab.
                .fallbackToDestructiveMigration()
                .addCallback(roomDatabaseCallback)
                .build()


        private val roomDatabaseCallback = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                INSTANCE?.also {
                    val dao = it.wordDao()
                    var words = arrayOf("dolphin", "crocodile", "cobra")
                    val insert = Observable.fromArray(*words)
                    Completable.fromAction { dao.deleteAll() }.subscribeOn(Schedulers.io())
                            .subscribe { insert.subscribeOn(Schedulers.io()).forEach { word -> dao.insert(Word(word)) } }


                }
            }
        }
    }

    abstract fun wordDao(): WordDao
}
