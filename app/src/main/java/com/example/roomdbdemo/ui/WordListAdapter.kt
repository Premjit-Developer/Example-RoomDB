package com.example.roomdbdemo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdbdemo.data.Word
import com.example.roomdbdemo.databinding.RecyclerviewItemBinding

class WordListAdapter(private val onItemClicked : (Word) -> Unit ): ListAdapter <Word, WordListAdapter.WordViewHolder> (WordsComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current.word, current.meaning)

    }

    class WordViewHolder(private val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(wText: String?,mText: String?) {
            binding.tvWord.text = wText
            binding.tvMeaning.text = mText
        }

    }

    class WordsComparator : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }
    }


}
