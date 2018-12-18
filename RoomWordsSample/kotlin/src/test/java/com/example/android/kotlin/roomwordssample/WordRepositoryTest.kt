package com.example.android.kotlin.roomwordssample

import android.os.Build.VERSION_CODES.JELLY_BEAN
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.android.kotlin.roomwordssample.util.LiveDataTestUtil
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [JELLY_BEAN], manifest = Config.NONE)
class WordRepositoryTest {

    companion object {
        private val wordList = MutableLiveData<List<Word>>()
    }

    @get:Rule // used to make all live data calls sync
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mWordDao = mock<WordDao> { on { getAlphabetizedWords() } doReturn wordList }
    private val mRepository= WordRepository.getInstance(mWordDao)


    @Before
    fun init() {
        ShadowLog.stream = System.out
    }

    // insert word
    @Test
    fun insert_word() {
        mRepository.insert(Word("aaa"))
        verify(mWordDao).insert(any())
    }

    // get all words
    @Test
    fun get_all_words() {
        val mockWords = mock<List<Word>>()
        wordList.postValue(mockWords)
        val allWords = LiveDataTestUtil.getValue(mRepository.allWords)

        Truth.assertThat(allWords).isEqualTo(mockWords)
    }
}