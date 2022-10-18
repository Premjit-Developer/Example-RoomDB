package com.example.roomdbdemo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomdbdemo.data.Word
import com.example.roomdbdemo.databinding.FragEditorBinding
import com.example.roomdbdemo.ui.WordApplication
import com.example.roomdbdemo.ui.WordViewModel


class FragEditor :Fragment() {

    private val navigationArgs: FragDetailsArgs by navArgs()
    private lateinit var mWord: Word

    private val wordViewModel: WordViewModel by activityViewModels {
        WordViewModel.WordViewModelFactory((activity?.application as WordApplication).repository)
    }



    private var _binding: FragEditorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragEditorBinding.inflate(inflater, container, false)
        return (binding.root)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.wordId
        if (id > 0) {
            wordViewModel.retrieveWordWithMeaning(id).asLiveData().observe(this.viewLifecycleOwner) { selectedItem ->
                mWord = selectedItem
                bind(mWord)
            }
        } else {
            binding.btnSave.setOnClickListener {
                addNewWordWithMeaning()
            }
        }
    }


    private fun bind(bWord: Word) {

        binding.apply {
            editWord.setText(bWord.word, TextView.BufferType.SPANNABLE)
            editMeaning.setText(bWord.meaning, TextView.BufferType.SPANNABLE)
            btnSave.setOnClickListener { updateWordWithMeaning() }
        }
    }

    private fun isEntryValid(): Boolean {
        return wordViewModel.isEntryValid(
            binding.editWord.text.toString(),
            binding.editMeaning.text.toString()
        )
    }

    private fun addNewWordWithMeaning() {
        if (isEntryValid()) {
            wordViewModel.addNewWords(
                binding.editWord.text.toString(),
                binding.editMeaning.text.toString()
            )
            val action = FragEditorDirections.actionFragEditorToFragHome()
            findNavController().navigate(action)
        }
    }

    private fun updateWordWithMeaning() {
        if (isEntryValid()) {
            wordViewModel.updateWords(
                navigationArgs.wordId,
                binding.editWord.text.toString(),
                binding.editMeaning.text.toString()
            )
            val action = FragEditorDirections.actionFragEditorToFragHome()
            findNavController().navigate(action)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}