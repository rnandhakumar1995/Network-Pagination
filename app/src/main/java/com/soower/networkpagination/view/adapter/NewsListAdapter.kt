package com.soower.networkpagination.view.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.soower.networkpagination.R
import com.soower.networkpagination.model.NetworkState
import com.soower.networkpagination.model.News
import com.soower.networkpagination.model.Status
import kotlinx.android.synthetic.main.news_item.view.*
import kotlinx.android.synthetic.main.progress_item.view.*

class NewsListAdapter : PagedListAdapter<News, RecyclerView.ViewHolder>(NewsDiffCallback) {
    private var networkState: NetworkState? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.news_item -> NewsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.news_item,
                    parent,
                    false
                )
            )
            R.layout.progress_item -> NetworkStateViewHoler(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.progress_item,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.news_item -> {
                val news = getItem(holder.adapterPosition) as News
                val newsViewHolder = holder as NewsViewHolder
                newsViewHolder.itemView.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(Uri.parse(news.url))
                    newsViewHolder.itemView.context.startActivity(intent)
                }
                newsViewHolder.textView.text =
                    ((holder.adapterPosition + 1).toString()) + " " + news.title

            }
            R.layout.progress_item -> (holder as NetworkStateViewHoler).bindTo(networkState)
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.progress_item
        } else {
            R.layout.news_item
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        if (currentList != null) {
            if (currentList!!.size != 0) {
                val previousState = this.networkState
                val hadExtraRow = hasExtraRow()
                this.networkState = newNetworkState
                val hasExtraRow = hasExtraRow()
                if (hadExtraRow != hasExtraRow) {
                    if (hadExtraRow) {
                        notifyItemRemoved(super.getItemCount())
                    } else {
                        notifyItemInserted(super.getItemCount())
                    }
                } else if (hasExtraRow && previousState !== newNetworkState) {
                    notifyItemChanged(itemCount - 1)
                }
            }
        }
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView

        init {
            textView = itemView.news_tv
        }
    }

    class NetworkStateViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar

        init {
            progressBar = itemView.progress_bar
        }

        fun bindTo(networkState: NetworkState?) {
            itemView.progress_bar.visibility =
                if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE
        }
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }
        }
    }
}