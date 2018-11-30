package com.example.android.kotlin.materialme

import android.content.Context
import android.content.Intent
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.android.kotlin.materialme.DetailActivity.Companion.EXTRA_IMAGE_RESOURCE
import com.example.android.kotlin.materialme.DetailActivity.Companion.EXTRA_SUB_TITLE
import com.example.android.kotlin.materialme.DetailActivity.Companion.EXTRA_TITLE

class SportsAdapter(private val context: Context): RecyclerView.Adapter<SportsAdapter.SportsViewHolder>() {
    private val mSports = mutableListOf<Sport>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportsViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        return SportsViewHolder(view)
    }

    override fun getItemCount()=mSports.size

    override fun onBindViewHolder(holder: SportsViewHolder, position: Int) {
        val currentSport = mSports[position]
        holder.mTitleText.text = currentSport.title
        holder.mSubTitleText.text = currentSport.subTitle
        Glide.with(context).load(currentSport.imageResource).into(holder.mSportsImage)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_TITLE, currentSport.title)
            intent.putExtra(EXTRA_SUB_TITLE, currentSport.subTitle)
            intent.putExtra(EXTRA_IMAGE_RESOURCE, currentSport.imageResource)

            context.startActivity(intent)
        }

    }

    fun addAllSports(sports: Array<Sport>) {
        val tempSports = listOf(*mSports.toTypedArray())
        mSports.clear()
        mSports.addAll(sports)
        val diffResult = DiffUtil.calculateDiff(SportsDiffCallback(tempSports, mSports))
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeSport(position: Int) {
        val tempSports = listOf(*mSports.toTypedArray())
        mSports.removeAt(position)
        val diffResult = DiffUtil.calculateDiff(SportsDiffCallback(tempSports, mSports))
        diffResult.dispatchUpdatesTo(this)
    }

    class SportsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mSportsImage=itemView.findViewById<ImageView>(R.id.sportsImage)
        val mTitleText = itemView.findViewById<TextView>(R.id.title)
        val mSubTitleText = itemView.findViewById<TextView>(R.id.subTitle)
    }

    class SportsDiffCallback(private val oldSports: List<Sport>, private val newSports: List<Sport>): DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldSports[oldItemPosition].title == newSports[newItemPosition].title
        }

        override fun getOldListSize(): Int {
            return oldSports.size
        }

        override fun getNewListSize(): Int {
            return newSports.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldSports[oldItemPosition].subTitle == newSports[newItemPosition].subTitle)
                    && (oldSports[oldItemPosition].imageResource == newSports[newItemPosition].imageResource)
        }

    }

}
