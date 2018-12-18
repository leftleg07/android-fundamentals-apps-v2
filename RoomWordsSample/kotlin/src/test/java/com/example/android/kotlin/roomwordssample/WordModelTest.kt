package com.example.android.kotlin.roomwordssample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.android.kotlin.roomwordssample.util.LiveDataTestUtil
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [16], manifest = Config.NONE)
class WordModelTest {

    companion object {
        private val wordList = MutableLiveData<List<Word>>()
    }

    @get:Rule // used to make all live data calls sync
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mMockRepository = mock<WordRepository> {
        on {allWords} doReturn wordList
    }
    private val mModel = WordViewModel(mMockRepository)

    // insert word
    @Test
    fun insert_word() {
        val word = mock<Word>()
        mModel.insert(word)
        verify(mMockRepository).insert(word)
    }

    // get all words
    @Test
    fun get_all_words() {
        val mockWords = mock<List<Word>>()
        wordList.postValue(mockWords)
        val allWords = LiveDataTestUtil.getValue(mModel.allWords)

        Truth.assertThat(allWords).isEqualTo(mockWords)
    }
}