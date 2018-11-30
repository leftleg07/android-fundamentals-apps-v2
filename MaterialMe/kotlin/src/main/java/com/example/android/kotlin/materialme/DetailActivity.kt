package com.example.android.kotlin.materialme

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_IMAGE_RESOURCE = "_extra_image_resource"
        const val EXTRA_TITLE = "_extra_title"
        const val EXTRA_SUB_TITLE = "_extra_sub_title"
    }

    private val mTitleText by lazy { findViewById<TextView>(R.id.titleDetail) }
    private val mSubTitleText by lazy { findViewById<TextView>(R.id.subTitleDetail) }
    private val mSportsImage by lazy { findViewById<ImageView>(R.id.sportsImageDetail) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val title = intent.getStringExtra(EXTRA_TITLE)
        val subTitle = intent.getStringExtra(EXTRA_SUB_TITLE)
        val imageResource = intent.getIntExtra(EXTRA_IMAGE_RESOURCE, 0)

        mTitleText.text = title
        mSubTitleText.text = subTitle
        Glide.with(this).load(imageResource).into(mSportsImage)

    }
}
