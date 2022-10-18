package com.example.roomdbdemo.ui

import androidx.lifecycle.*
import com.example.roomdbdemo.data.Word
import com.example.roomdbdemo.data.WordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WordViewModel (private val repository: WordRepository) : ViewModel() {

    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()


    fun isEntryValid(wordWord: String, wordMeaning: String): Boolean {
        if (wordWord.isBlank() || wordMeaning.isBlank()) {
            return false
        }
        return true
    }




    private  fun getNewWordsEntry(word : String, meaning : String): Word {
        return Word(
            word = word,
            meaning= meaning
        )
    }

    private fun getUpdatedWordsEntry(wordId: Int, word: String, meaning: String): Word {
        return Word(
            id = wordId,
            word = word,
            meaning= meaning
        )
    }




    fun addNewWords(wordName: String, wordMeaning: String) {
        val newItem = getNewWordsEntry(wordName, wordMeaning)
        insertWord(newItem)
    }

    fun updateWords(wordId: Int, word: String, meaning: String) {
        val updatedItem = getUpdatedWordsEntry(wordId, word, meaning)
        updateWord(updatedItem)
    }

    fun deleteWord(word: Word) = viewModelScope.launch{
        repository.delete(word)
    }

    fun retrieveWordWithMeaning(id: Int): Flow<Word> {
        return repository.getWordById(id)
    }

    private fun insertWord(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
    private fun updateWord(word: Word) = viewModelScope.launch {
        repository.update(word)
    }




    class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WordViewModel(repository) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}