package com.example.mymoviddb.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviddb.adapters.MoviesAdapter
import com.example.mymoviddb.adapters.TVAdapter
import com.example.mymoviddb.databinding.FragmentHomeBinding
import com.example.mymoviddb.datasource.remote.RemoteServerAccess
import com.example.mymoviddb.utils.DeviceUtils

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel by viewModels<HomeViewModel> {
        val remoteSource = RemoteServerAccess()
        HomeViewModel.Factory(remoteSource)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel

        initializeAdapter()
        setClickListener()

        return binding.root
    }

    fun initializeAdapter() {
        // Adapter for popular movies
        binding.popularMovieRv.adapter = MoviesAdapter()
        binding.popularMovieRv.layoutManager = PreloadLinearLayout(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        ).apply {
            setExtraLayoutSpace(DeviceUtils.getScreenWidth(requireContext()) * 4)
        }

        // Adapter for now playing movies
        binding.nowPlayingMovieRv.adapter = MoviesAdapter()
        binding.nowPlayingMovieRv.layoutManager = PreloadLinearLayout(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        ).apply {
            setExtraLayoutSpace(DeviceUtils.getScreenWidth(requireContext()) * 4)
        }

        // Adapter for popular tv shows
        binding.popularTvRv.adapter = TVAdapter()
        binding.popularTvRv.layoutManager = PreloadLinearLayout(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        ).apply {
            setExtraLayoutSpace(DeviceUtils.getScreenWidth(requireContext()) * 4)
        }

        // Adapter for on air tv shows
        binding.onAirPopularTvRv.adapter = TVAdapter()
        binding.onAirPopularTvRv.layoutManager = PreloadLinearLayout(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        ).apply {
            setExtraLayoutSpace(DeviceUtils.getScreenWidth(requireContext()) * 4)
        }
    }

    private fun setClickListener() {
        binding.errorPopularMoviesMessage.tryAgainButton.setOnClickListener {
            homeViewModel.getPopularMovieList()
        }
        binding.errorNowPlayingMoviesMessage.tryAgainButton.setOnClickListener {
            homeViewModel.getNowPlayingMovieList()
        }
        binding.errorPopularTvMessage.tryAgainButton.setOnClickListener {
            homeViewModel.getPopularTVList()
        }
        binding.errorOnAirTvMessage.tryAgainButton.setOnClickListener {
            homeViewModel.getonAirTVList()
        }
    }

}