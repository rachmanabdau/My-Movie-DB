package com.example.mymoviddb.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviddb.R
import com.example.mymoviddb.adapters.MoviesAdapter
import com.example.mymoviddb.adapters.TVAdapter
import com.example.mymoviddb.category.movie.MovieDataSource
import com.example.mymoviddb.category.tv.TVDataSource
import com.example.mymoviddb.databinding.FragmentHomeBinding
import com.example.mymoviddb.utils.DeviceUtils
import com.example.mymoviddb.utils.EventObserver
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel

        initializeAdapter()
        setClickListener()

        homeViewModel.snackbarMessage.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })

        return binding.root
    }

    private fun initializeAdapter() {
        // Adapter for popular movies
        binding.popularMovieRv.adapter = MoviesAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCategoryMovieListFragment(
                    MovieDataSource.POPULAR_MOVIE_ID, R.string.popular_movie_list_contentDesc
                )
            )
        }
        binding.popularMovieRv.layoutManager = PreloadLinearLayout(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        ).apply {
            setExtraLayoutSpace(DeviceUtils.getScreenWidth(requireContext()) * 4)
        }

        // Adapter for now playing movies
        binding.nowPlayingMovieRv.adapter = MoviesAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCategoryMovieListFragment(
                    MovieDataSource.NOW_PLAYING_MOVIE_ID,
                    R.string.now_playing_movie_list_contentDesc
                )
            )
        }
        binding.nowPlayingMovieRv.layoutManager = PreloadLinearLayout(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        ).apply {
            setExtraLayoutSpace(DeviceUtils.getScreenWidth(requireContext()) * 4)
        }

        // Adapter for popular tv shows
        binding.popularTvRv.adapter = TVAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCategoryTvFragment(
                    TVDataSource.POPULAR_TV_ID, R.string.popular_tv_show_list_contentDesc
                )
            )
        }
        binding.popularTvRv.layoutManager = PreloadLinearLayout(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        ).apply {
            setExtraLayoutSpace(DeviceUtils.getScreenWidth(requireContext()) * 4)
        }

        // Adapter for on air tv shows
        binding.onAirPopularTvRv.adapter = TVAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCategoryTvFragment(
                    TVDataSource.NOW_PLAYING_TV_ID, R.string.now_airing_tv_show_list_contentDesc
                )
            )
        }
        binding.onAirPopularTvRv.layoutManager = PreloadLinearLayout(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        ).apply {
            setExtraLayoutSpace(DeviceUtils.getScreenWidth(requireContext()) * 4)
        }
    }

    private fun setClickListener() {
        binding.errorPopularMoviesMessage.tryAgainButton.setOnClickListener {
            lifecycleScope.launch {
                binding.errorPopularMoviesMessage.tryAgainButton.isEnabled = false
                homeViewModel.getPopularMovieList()
                binding.errorPopularMoviesMessage.tryAgainButton.isEnabled = true
            }
        }
        binding.errorNowPlayingMoviesMessage.tryAgainButton.setOnClickListener {
            lifecycleScope.launch {
                binding.errorNowPlayingMoviesMessage.tryAgainButton.isEnabled = false
                homeViewModel.getNowPlayingMovieList()
                binding.errorNowPlayingMoviesMessage.tryAgainButton.isEnabled = true
            }
        }
        binding.errorPopularTvMessage.tryAgainButton.setOnClickListener {
            lifecycleScope.launch {
                binding.errorPopularTvMessage.tryAgainButton.isEnabled = false
                homeViewModel.getPopularTVList()
                binding.errorPopularTvMessage.tryAgainButton.isEnabled = true
            }
        }
        binding.errorOnAirTvMessage.tryAgainButton.setOnClickListener {
            lifecycleScope.launch {
                binding.errorOnAirTvMessage.tryAgainButton.isEnabled = false
                homeViewModel.getonAirTVList()
                binding.errorOnAirTvMessage.tryAgainButton.isEnabled = true
            }
        }
    }

}