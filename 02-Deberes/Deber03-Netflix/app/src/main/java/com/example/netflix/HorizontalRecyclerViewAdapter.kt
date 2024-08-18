package com.example.netflix

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class HorizontalRecyclerViewAdapter(private val imageList: List<Int>) : RecyclerView.Adapter<HorizontalRecyclerViewAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.imgMoviePoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.productImage.setImageResource(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size



}
