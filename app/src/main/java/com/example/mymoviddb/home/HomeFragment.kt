package com.example.mymoviddb.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
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
import com.example.mymoviddb.utils.Util
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

        Util.setupToolbar(binding.homeToolbar.toolbar, findNavController())

        initializeAdapter()
        setClickListener()

        homeViewModel.snackbarMessage.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)

        /*// Get the SearchView and set the searchable configuration
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(ComponentName(requireContext(), SearchActivity::class.java)))
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
        }*/

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                val searchManager =
                    requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initializeAdapter() {
        // Adapter for popular movies
        binding.popularMovieRv.adapter = MoviesAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCategoryMovieListFragment(
                    R.string.popular_movie_list_contentDesc
                )
            )
            MovieDataSource.MOVIE_CATEGORY_ID = MovieDataSource.POPULAR_MOVIE_ID
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
                    R.string.now_playing_movie_list_contentDesc
                )
            )

            MovieDataSource.MOVIE_CATEGORY_ID = MovieDataSource.NOW_PLAYING_MOVIE_ID
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
                    R.string.popular_tv_show_list_contentDesc
                )
            )
            TVDataSource.TV_CATEGORY_ID = TVDataSource.POPULAR_TV_ID
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
                    R.string.now_airing_tv_show_list_contentDesc
                )
            )
            TVDataSource.TV_CATEGORY_ID = TVDataSource.ON_AIR_TV_ID
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