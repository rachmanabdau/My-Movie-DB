package com.example.mymoviddb.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviddb.adapters.MoviesAdapter
import com.example.mymoviddb.databinding.FragmentHomeBinding
import com.example.mymoviddb.datasource.remote.RemoteServerAccess
import com.example.mymoviddb.model.Result

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel by viewModels<HomeViewModel> {
        val remoteSource = RemoteServerAccess()
        HomeViewModel.Factory(requireActivity().application, remoteSource)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val adapter = MoviesAdapter()

        homeViewModel.getMovieList()
        homeViewModel.movieList.observe(viewLifecycleOwner, {
            if (it is Result.Success && it.data != null) {
                adapter.submitList(it.data.results)
                binding.popularMovieRv.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.popularMovieRv.adapter = adapter
            }
        })

        return binding.root
    }

}