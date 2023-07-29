package com.example.baseproject.ui.firebase.fragment.image.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.baseproject.R
import com.example.baseproject.databinding.ListItemImageBinding
import com.example.baseproject.ui.base.BaseViewHolder
import com.example.baseproject.ui.firebase.fragment.image.model.ModelImage

class ImageListAdapter : ListAdapter<ModelImage, BaseViewHolder>(DiffCallback) {

    private var click: ((item: ModelImage) -> Unit)? = null

    private var context: Context? = null

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.onBind(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_image, parent, false)
    )

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        private val binding = ListItemImageBinding.bind(view)

        override fun clear() {
        }

        @SuppressLint("SetTextI18n")
        override fun onBind(position: Int) {
            with(getItem(position)) {
                url.let { url ->
                    context?.let {
                        Glide.with(it).load(url).error(R.drawable.ic_place_holder)
                            .placeholder(R.drawable.ic_place_holder)
                            .into(binding.ivPreviewPhoto)
                    }
                }

                itemView.setOnClickListener {
                    click?.invoke(this)
                }
            }
        }
    }

    fun click(click: (item: ModelImage) -> Unit) {
        this.click = click
    }

    fun getContext(context: Context) {
        this.context = context
    }

    private object DiffCallback : DiffUtil.ItemCallback<ModelImage>() {
        override fun areItemsTheSame(
            oldItem: ModelImage,
            newItem: ModelImage
        ): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ModelImage,
            newItem: ModelImage
        ): Boolean =
            oldItem == newItem
    }

}

