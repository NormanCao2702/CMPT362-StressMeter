package com.example.tranquangngoc_cao_stressmeter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class ImageAdapter(private val context: Context, private var images: List<Int>) : BaseAdapter() {

    override fun getCount(): Int = images.size
    override fun getItem(position: Int): Any = images[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView: ImageView
        if (convertView == null) {
            imageView = ImageView(context).apply {
                val screenWidth = context.resources.displayMetrics.widthPixels
                val imageSize = screenWidth / 4 // Divide by 4 for 4 columns
                layoutParams = ViewGroup.LayoutParams(imageSize, imageSize)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        } else {
            imageView = convertView as ImageView
        }

        imageView.setImageResource(images[position])
        return imageView
    }

    fun updateImages(newImages: List<Int>) {
        images = newImages
        notifyDataSetChanged()
    }

}
