package com.example.finalassignementapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewsViewModel: ViewModel() {

    var news = mutableStateOf(listOf<News>())


    fun addNews(new: News){
        var newNews = news.value.toMutableList()
        newNews.add(new)
        news.value = newNews
    }

}