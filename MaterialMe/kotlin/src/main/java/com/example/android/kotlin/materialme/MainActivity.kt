package com.example.android.kotlin.materialme

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback
import android.view.View

class MainActivity : AppCompatActivity() {

    private val mRecycler by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val mSportsAdapter by lazy {SportsAdapter(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecycler.layoutManager = LinearLayoutManager(this)
        mRecycler.adapter = mSportsAdapter

        val helper = ItemTouchHelper(object: SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                mSportsAdapter.removeSport(position)
            }
        })
        helper.attachToRecyclerView(mRecycler)

        initializeData()

    }

    private fun initializeData() {
        val titles = resources.getStringArray(R.array.sports_titles)
        val subTitles = resources.getStringArray(R.array.sports_info)
        val sportsImages = resources.obtainTypedArray(R.array.sports_images)

        val sports = Array(titles.size) {
            Sport(titles[it], subTitles[it], sportsImages.getResourceId(it, 0)) }
        mSportsAdapter.addAllSports(sports)

        sportsImages.recycle()
        mRecycler.smoothScrollToPosition(0)

    }

    fun resetSports(view: View) {
        initializeData()
    }
}
