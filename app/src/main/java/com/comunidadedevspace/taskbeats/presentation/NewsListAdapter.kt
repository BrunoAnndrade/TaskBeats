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
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.local.News
import com.comunidadedevspace.taskbeats.data.local.Task

class NewsListAdapter:ListAdapter<News, NewsListViewHolder>(NewsListAdapter) {

    //criando uma view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
       val news = getItem(position)
        holder.bind(news)
    }


    //classe auxiliar da biblioteca android para comparar dois objetos equivalentes
    companion object : DiffUtil.ItemCallback<News>() {

        //comparando todas as tasks
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }

        //comparando conteudo para analisar alterações, se algo mudar, ele vai alterar
        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.imgUrl == newItem.imgUrl
        }

    }
}

//recuperando o modelo de view criado no XML
class NewsListViewHolder(
    private val NewsView: View
) : RecyclerView.ViewHolder(NewsView) {

    val newsTitle: TextView = NewsView.findViewById(R.id.tv_news_title)
    val imgUrl: ImageView = NewsView.findViewById(R.id.iv_news)

    fun bind(
        news:News,
    ) {
       newsTitle.text = news.title
       imgUrl.load(news.imgUrl)

    }


}