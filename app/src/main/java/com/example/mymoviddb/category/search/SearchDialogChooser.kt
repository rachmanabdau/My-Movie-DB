package com.example.mymoviddb.category.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mymoviddb.category.movie.MovieDataSource
import com.example.mymoviddb.category.tv.TVDataSource
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

        binding.movieChooserContainer.setOnClickListener {
            findNavController().navigate(
                SearchDialogChooserDirections.actionSearchDialogChooserToSearchActivity(
                    MovieDataSource.SEARCH_MOVIES
                )
            )
            this.dismiss()
        }

        binding.tvChooserContainer.setOnClickListener {
            findNavController().navigate(
                SearchDialogChooserDirections.actionSearchDialogChooserToSearchActivity(TVDataSource.SEARCH_TV)
            )
            this.dismiss()
        }

        return binding.root
    }
}