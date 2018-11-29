package com.example.android.kotlin.tabexperiment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private val mToolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val mPager by lazy { findViewById<ViewPager>(R.id.pager) }
    private val mTabLayout by lazy { findViewById<TabLayout>(R.id.tab_layout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mToolbar)

        mTabLayout.setupWithViewPager(mPager)
        val adapter = MyPageAdapter(supportFragmentManager, this)
        mPager.adapter = adapter

    }
}
