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
import com.example.roomdbdemo.R
import com.example.roomdbdemo.data.Word
import com.example.roomdbdemo.databinding.FragDetailsBinding
import com.example.roomdbdemo.ui.WordApplication
import com.example.roomdbdemo.ui.WordViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class FragDetails : Fragment() {

    private val navigationArgs: FragDetailsArgs by navArgs()
    private lateinit var word: Word

    private val wordViewModel: WordViewModel by activityViewModels {
        WordViewModel.WordViewModelFactory((activity?.application as WordApplication).repository)
    }


    private var _binding: FragDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragDetailsBinding.inflate(inflater, container, false)
        return (binding.root)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.wordId
        wordViewModel.retrieveWordWithMeaning(id).asLiveData()
            .observe(this.viewLifecycleOwner) { selectedItem ->
                word = selectedItem
                bind(word)
            }
    }


    private fun bind(word: Word) {
        binding.apply {
            tvDWord.setText(word.word, TextView.BufferType.SPANNABLE)
            tvDMeaning.setText(word.meaning, TextView.BufferType.SPANNABLE)
            btnDelete.setOnClickListener { showConfirmationDialog() }
            btnFabEdit.setOnClickListener { editItem() }
        }
    }

    private fun editItem() {
        val action = FragDetailsDirections.actionFragDetailsToFragEditor(getString(R.string.frag_editor), word.id)
        findNavController().navigate(action)
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
            }
            .show()
    }

    private fun deleteItem() {
        wordViewModel.deleteWord(word)
        findNavController().navigateUp()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}