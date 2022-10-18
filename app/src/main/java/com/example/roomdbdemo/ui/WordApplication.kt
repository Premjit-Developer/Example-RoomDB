package com.example.roomdbdemo.ui

import android.app.Application
import com.example.roomdbdemo.data.WordRepository
import com.example.roomdbdemo.data.WordRoomDatabase

class WordApplication : Application(){
    private val database by lazy { WordRoomDatabase.getDatabase(this) }
    val repository by lazy { WordRepository(database.wordDao()) }
}