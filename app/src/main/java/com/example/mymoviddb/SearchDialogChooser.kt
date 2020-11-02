package com.example.mymoviddb

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mymoviddb.category.SearchActivity
import com.example.mymoviddb.databinding.ShowChooserDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchDialogChooser : BottomSheetDialogFragment() {

    companion object {
        const val SEARCH_ID = "com.example.mymoviddb.SEARCHID"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ShowChooserDialogBinding.inflate(inflater, container, false)
        val intent = Intent(requireContext(), SearchActivity::class.java)

        binding.movieChooserContainer.setOnClickListener {
            requireActivity().onSearchRequested()
            intent.putExtra(SEARCH_ID, SearchActivity.SEARCH_MOVIES)
            startActivity(intent)
            this.dismiss()
        }

        binding.tvChooserContainer.setOnClickListener {
            intent.putExtra(SEARCH_ID, SearchActivity.SEARCH_TV)
            startActivity(intent)
            this.dismiss()
        }

        return binding.root
    }
}