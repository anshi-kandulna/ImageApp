package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter(val context: MainActivity, val photosArrayList: MutableList<Photo>):
RecyclerView.Adapter<MyAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_photo,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return photosArrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val photo = photosArrayList[position]

        // Set the photographer's name
        holder.title.text = photo.photographer

        // Load the image using Glide
        Glide.with(context)
            .load(photo.src.medium) // Using the "medium" image from the `Src` data class
            .into(holder.image) // Set the image into the ImageView
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var title : TextView
        var image : ImageView

        init {
            title = itemView.findViewById(R.id.textView)
            image = itemView.findViewById(R.id.imageView)
        }
    }

    // Method to add new items to the adapter
    fun addItems(newPhotos: List<Photo>) {
        val positionStart = photosArrayList.size
        photosArrayList.addAll(newPhotos)
        notifyItemRangeInserted(positionStart, newPhotos.size)
    }

}

