package com.example.baseproject.ui.firebase.fragment.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.baseproject.R
import com.example.baseproject.databinding.ListItemTextBinding
import com.example.baseproject.ui.base.BaseViewHolder
import com.example.baseproject.ui.firebase.fragment.list.model.ModelList

class TextListAdapter : ListAdapter<ModelList, BaseViewHolder>(DiffCallback) {

    private var click: ((item: ModelList) -> Unit)? = null

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.onBind(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_text, parent, false)
    )

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        private val binding = ListItemTextBinding.bind(view)

        override fun clear() {
            binding.tvText.text = ""
        }

        @SuppressLint("SetTextI18n")
        override fun onBind(position: Int) {
            with(getItem(position)) {
                name.let { name -> binding.tvText.text = name }

                itemView.setOnClickListener {
                    click?.invoke(this)
                }
            }
        }
    }

    fun click(click: (item: ModelList) -> Unit) {
        this.click = click
    }

    private object DiffCallback : DiffUtil.ItemCallback<ModelList>() {
        override fun areItemsTheSame(
            oldItem: ModelList,
            newItem: ModelList
        ): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ModelList,
            newItem: ModelList
        ): Boolean =
            oldItem == newItem
    }

}

