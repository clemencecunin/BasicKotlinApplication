package com.example.finalassignementapp
import androidx.compose.runtime.MutableState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

data class News (val message : String, val author : String, val date : String)

fun sendNews(news : News){
    Firebase.firestore
        .collection("news")
        .add(News(news.message,news.author,news.date))
}

fun getAllNews(
    allnews: MutableState<List<News>>,
) {
    Firebase.firestore
        .collection("news")
        .get()
        .addOnSuccessListener {
            val firenews = mutableListOf<News>()
            for(news in it){
                firenews.add(News(news.get("message").toString(),news.get("author").toString(),
                    news.get("date").toString())
                )
            }

            allnews.value = firenews
        }
}


