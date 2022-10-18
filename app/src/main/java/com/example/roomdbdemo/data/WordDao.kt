package com.example.roomdbdemo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY Word ASC ")
    fun getAllWordsAndMeanings() : Flow<List<Word>>

    @Query("SELECT * from word_table WHERE id = :id")
    fun getWordById(id: Int): Flow<Word>

    @Insert( onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord( word : Word)

    @Update
    suspend fun updateWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)

}