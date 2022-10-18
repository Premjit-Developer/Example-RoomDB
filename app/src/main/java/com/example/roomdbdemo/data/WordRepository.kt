package com.example.roomdbdemo.data

import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {

    val allWords: Flow <List<Word>> = wordDao.getAllWordsAndMeanings()

    suspend fun insert(word: Word){
        wordDao.insertWord(word)
    }

    suspend fun update(word: Word){
        wordDao.updateWord(word)
    }

    suspend fun delete(word: Word){
        wordDao.deleteWord(word)
    }

    fun getWordById(id: Int): Flow <Word> {
        return wordDao.getWordById(id)
    }

}
