package com.example.android.kotlin.recyclerview

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class WordListAdapter(items: Array<String>) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {
    private val mWordList = mutableListOf(*items)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.wordlist_item, parent, false)
        return WordViewHolder(view)
    }

    override fun getItemCount() = mWordList.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.mWordText?.apply { text = mWordList[position] }
        holder.itemView.setOnClickListener {
            val tempWordList = listOf(*mWordList.toTypedArray())
            mWordList[position] += " Clicked"
            val diffResult = DiffUtil.calculateDiff(WordListDiffCallback(tempWordList, mWordList))
            diffResult.dispatchUpdatesTo(this)
        }
    }

    fun addWord(word: String) {
        val tempWordList = listOf(*mWordList.toTypedArray())
        mWordList.add(word)
        val diffResult = DiffUtil.calculateDiff(WordListDiffCallback(tempWordList, mWordList))
        diffResult.dispatchUpdatesTo(this)
    }

    class WordListDiffCallback(private val oldWordList: List<String>, private val newWordList: List<String>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldWordList[oldItemPosition] == newWordList[newItemPosition]

        override fun getOldListSize() = oldWordList.size

        override fun getNewListSize() = newWordList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldWordList[oldItemPosition] == newWordList[newItemPosition]

    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mWordText by lazy { itemView.findViewById<TextView>(R.id.word) }
    }
}
