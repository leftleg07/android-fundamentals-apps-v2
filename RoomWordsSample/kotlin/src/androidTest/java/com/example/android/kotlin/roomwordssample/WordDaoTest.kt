package com.example.android.kotlin.roomwordssample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.kotlin.roomwordssample.util.LiveDataTestUtil
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class WordDaoTest {

    @get:Rule // used to make all live data calls sync
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mWordDao: WordDao
    private lateinit var mDb: WordRoomDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        mDb = Room.inMemoryDatabaseBuilder(context, WordRoomDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        mWordDao = mDb.wordDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDb.close()
    }

    // insert word
    @Test
    fun insert_word() {
        val word = Word("Love")
        mWordDao.insert(word)
        val allWords = LiveDataTestUtil.getValue(mWordDao.getAlphabetizedWords())
        Truth.assertThat(allWords[0]).isEqualTo(word)
    }

    // get all words sorted asc order
    @Test
    fun get_all_words_alphabetic_order() {
        val words = arrayOf(Word("ddd"), Word("aaa"), Word("ccc"), Word("bbb"))
        words.forEach { mWordDao.insert(it) }
        val allWords = LiveDataTestUtil.getValue(mWordDao.getAlphabetizedWords())
        words.sort()
        allWords.forEachIndexed { i, word -> Truth.assertThat(word).isEqualTo(words[i]) }
    }

    // delete all words
    @Test
    fun delete_all_words() {
        val words = arrayOf(Word("ddd"), Word("aaa"), Word("ccc"), Word("bbb"))
        words.forEach { mWordDao.insert(it) }
        mWordDao.deleteAll()
        val allWords = LiveDataTestUtil.getValue(mWordDao.getAlphabetizedWords())
        Truth.assertThat(allWords).isEmpty()

    }
}