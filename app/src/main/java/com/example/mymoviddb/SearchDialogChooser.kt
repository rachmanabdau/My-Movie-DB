package com.example.mymoviddb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mymoviddb.databinding.ShowChooserDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchDialogChooser : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ShowChooserDialogBinding.inflate(inflater, container, false)

        return binding.root
    }
}