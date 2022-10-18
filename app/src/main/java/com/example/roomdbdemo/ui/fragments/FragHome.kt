package com.example.roomdbdemo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdbdemo.R
import com.example.roomdbdemo.databinding.FragHomeBinding
import com.example.roomdbdemo.ui.WordApplication
import com.example.roomdbdemo.ui.WordListAdapter
import com.example.roomdbdemo.ui.WordViewModel


class FragHome : Fragment() {

    private val wordViewModel: WordViewModel by activityViewModels {
        WordViewModel.WordViewModelFactory((activity?.application as WordApplication).repository)
    }


    private var _binding: FragHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragHomeBinding.inflate(inflater, container, false)
        return (binding.root)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = WordListAdapter {
            val action = FragHomeDirections.actionFragHomeToFragDetails(it.id)
            findNavController().navigate(action)
        }
            binding.recyclerview.adapter = adapter
            binding.recyclerview.layoutManager = LinearLayoutManager(this.context)
            binding.fab.setOnClickListener {

            val action = FragHomeDirections.actionFragHomeToFragEditor(getString(R.string.frag_editor))
            findNavController().navigate(action)
        }

        wordViewModel.allWords.observe(this.viewLifecycleOwner) { words ->
            words?.let {
                adapter.submitList(it)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}