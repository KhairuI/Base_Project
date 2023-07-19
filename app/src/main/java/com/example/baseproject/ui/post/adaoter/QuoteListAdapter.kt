package com.example.baseproject.ui.post.adaoter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.baseproject.R
import com.example.baseproject.data.network.response.Quote
import com.example.baseproject.databinding.ListItemQuoteBinding
import com.example.baseproject.ui.base.BaseViewHolder

class QuoteListAdapter : ListAdapter<Quote.QuoteItem, BaseViewHolder>(DiffCallback) {

    private var click: ((item: Quote.QuoteItem) -> Unit)? = null

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.onBind(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_quote, parent, false)
    )

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        private val binding = ListItemQuoteBinding.bind(view)

        override fun clear() {
            binding.tvID.text = ""
            binding.tvQuoteAuthor.text = ""
            binding.tvQuoteText.text = ""
        }

        @SuppressLint("SetTextI18n")
        override fun onBind(position: Int) {
            with(getItem(position)) {
                id?.let { id -> binding.tvID.text = id.toString() }
                quote?.let { quote -> binding.tvQuoteText.text = quote }
                author?.let { author -> binding.tvQuoteAuthor.text = author }

                itemView.setOnClickListener {
                    click?.invoke(this)
                }
            }
        }
    }

    fun click(click: (item: Quote.QuoteItem) -> Unit) {
        this.click = click
    }

    private object DiffCallback : DiffUtil.ItemCallback<Quote.QuoteItem>() {
        override fun areItemsTheSame(
            oldItem: Quote.QuoteItem,
            newItem: Quote.QuoteItem
        ): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Quote.QuoteItem,
            newItem: Quote.QuoteItem
        ): Boolean =
            oldItem == newItem
    }

}

