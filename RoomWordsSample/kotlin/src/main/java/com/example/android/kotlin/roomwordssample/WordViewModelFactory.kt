package com.example.android.kotlin.roomwordssample

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WordViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val repository = WordRepository.getInstance(context)
       return WordViewModel(repository) as T
    }
}