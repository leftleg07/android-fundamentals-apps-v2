package com.example.android.kotlin.recyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val mRecycler by lazy { findViewById<RecyclerView>(R.id.recyclerview) }
    private val mFab by lazy { findViewById<FloatingActionButton>(R.id.fab) }
    private val mAdapter = WordListAdapter(Array(40) { "Word $it" })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecycler.layoutManager = LinearLayoutManager(this)
        mRecycler.adapter = mAdapter
        mFab.setOnClickListener {
            val wordSize = mAdapter.itemCount
            mAdapter.addWord("Word $wordSize")
            mRecycler.smoothScrollToPosition(wordSize)
        }
    }
}
