package com.example.newsapp.app.resources.news.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.app.resources.news.data.dao.NewsDao
import com.example.newsapp.app.resources.news.data.model.News

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase(

){
    companion object {
        const val DATABASE_NAME = "news_db"

        @JvmStatic
        fun getDatabase(context: Context): NewsDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = NewsDatabase::class.java,
                name = DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun newsDao() : NewsDao
}