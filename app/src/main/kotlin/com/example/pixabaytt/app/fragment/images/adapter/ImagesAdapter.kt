package com.example.pixabaytt.app.fragment.images.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.pixabaytt.app.data.domain.ImagesInfoModel
import com.example.pixabaytt.app.utils.autoNotify
import com.example.pixabaytt.databinding.ItemImageBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import java.lang.Exception
import kotlin.properties.Delegates


class ImagesAdapter(
    private val onImageClicked: (imageInfo: ImagesInfoModel) -> Unit
) : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    var items: List<ImagesInfoModel> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.id == n.id }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemImageBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(view, onImageClicked)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size


    class ViewHolder(
        private val binding: ItemImageBinding,
        private val onImageClicked: (imagesInfo: ImagesInfoModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imagesInfo: ImagesInfoModel) = with(binding) {

            Picasso.get().load(imagesInfo.webFormatURL).into(object :com.squareup.picasso.Target{
                override fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {
                    bitmap?.let {
                        val ratio = it.height.toFloat() / it.width.toFloat()
                        val heightFloat = itemView.width.toFloat() * ratio
                        val layoutParams = image.layoutParams as MarginLayoutParams
                        layoutParams.height = heightFloat.toInt()
                        layoutParams.width = itemView.width
                        image.layoutParams = layoutParams
                        image.setImageBitmap(bitmap)
                    }

                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

            })

            itemView.setOnClickListener {
                onImageClicked.invoke(imagesInfo)
            }
        }
    }
}