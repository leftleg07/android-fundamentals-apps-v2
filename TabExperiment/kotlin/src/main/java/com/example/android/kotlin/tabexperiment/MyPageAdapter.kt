package com.example.android.kotlin.tabexperiment

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class MyPageAdapter(fm: FragmentManager, private val context: Context) : FragmentStatePagerAdapter(fm) {

    private val mItemTitleResIds = arrayOf(R.string.tab_label1, R.string.tab_label2, R.string.tab_label3)
    private val mItems = arrayOf(TabFragment1(), TabFragment2(), TabFragment3())
    override fun getItem(position: Int) = mItems[position]
    override fun getCount() = mItems.size
    override fun getPageTitle(position: Int): CharSequence {
        return context.getString(mItemTitleResIds[position])
    }
}
