package com.example.test.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.test.databinding.ItemImageBinding

class ImageAdapter(private val imageUris: MutableList<String>,var context :Context) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val uri = imageUris[position]
        holder.bind(uri,position)
    }

    override fun getItemCount(): Int = imageUris.size

    inner class ImageViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(uri: String,position:Int) {
            Glide.with(context)
                .load(uri)
                .apply(RequestOptions().centerCrop())
                .into( binding.imageViewThumbnail)
            binding.cancelButton.setOnClickListener {
                if (position >= 0 && position < imageUris.size) {
                    imageUris.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, imageUris.size)
                }
            }
        }
    }
}
