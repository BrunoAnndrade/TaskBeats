package com.comunidadedevspace.taskbeats.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.News
import com.comunidadedevspace.taskbeats.data.Task

class NewListAdapter:ListAdapter<News,NewsListViewHolder>(NewListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
       val news = getItem(position)
        holder.bind(news)
    }

    companion object : DiffUtil.ItemCallback<News>() {

        //comparando todas as tasks
        override fun areItemsTheSame(oldItem:News, newItem: News): Boolean {
            return oldItem == newItem
        }

        //comparando conteudo para analisar alterações, se algo mudar, ele vai alterar
        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.imageUrl== newItem.imageUrl
        }

    }


}

class NewsListViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {

    val NewsTitle: TextView = view.findViewById(R.id.tb_news_title)
    val ImageNews: ImageView = view.findViewById(R.id.iv_news)


    fun bind(
        news: News,

    ){
        NewsTitle.text = news.title
        ImageNews.load(news.imageUrl){
            transformations(RoundedCornersTransformation(12f))
        }
    }


}