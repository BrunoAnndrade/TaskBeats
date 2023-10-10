package com.comunidadedevspace.taskbeats.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.data.remote.NewsDto
import com.comunidadedevspace.taskbeats.data.remote.NewsService
import com.comunidadedevspace.taskbeats.data.remote.RetrofitModule
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val newsService: NewsService
):ViewModel() {

    private val _newsListLiveData = MutableLiveData<List<NewsDto>>()

    val newsListLiveData:LiveData<List<NewsDto>> = _newsListLiveData


    //toda vez que iniciar o viewmodel ele vai no backend procurar as noticias
    init {
        getNewsList()
    }

    private fun getNewsList(){
        viewModelScope.launch {

            // caso tenha um erro de internet o app nao vai crashar
            try {
                val topNews = newsService.fetchTopNews().data
                val allNews = newsService.fetchAllNews().data
                _newsListLiveData.value = topNews + allNews

            } catch (ex: Exception){
                ex.printStackTrace()
            }

        }
    }


    companion object{
        fun create():NewsListViewModel{
            val newsService = RetrofitModule.createNewsService()
            return NewsListViewModel(newsService)

        }
    }
}