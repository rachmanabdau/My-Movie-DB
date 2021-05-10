package com.example.mymoviddb.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviddb.R
import com.example.mymoviddb.adapters.PlaceHolderAdapter
import com.example.mymoviddb.adapters.PreviewShowAdapter
import com.example.mymoviddb.category.ShowCategoryIndex
import com.example.mymoviddb.databinding.FragmentHomeBinding
import com.example.mymoviddb.detail.DetailActivity
import com.example.mymoviddb.model.ShowResult
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
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel
        setHasOptionsMenu(true)

        initializeAdapter()
        setClickListener()

        homeViewModel.snackbarMessage.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSearchDialogChooser()
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeAdapter() {
        initiatePlaceHolderAdapter()
        // Adapter for popular movies
        binding.popularMovieRv.adapter = PreviewShowAdapter({
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCategoryMovieListFragment(
                    R.string.popular_movie_list_contentDesc, ShowCategoryIndex.POPULAR_MOVIES
                )
            )
        }, {
            navigateToDetailMovie(it)
        })
        binding.popularMovieRv.layoutManager = PreloadLinearLayout(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )

        // Adapter for now playing movies
        binding.nowPlayingMovieRv.adapter = PreviewShowAdapter({
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCategoryMovieListFragment(
                    R.string.now_playing_movie_list_contentDesc,
                    ShowCategoryIndex.NOW_PLAYING_MOVIES
                )
            )
        }, {
            navigateToDetailMovie(it)
        })
        binding.nowPlayingMovieRv.layoutManager = PreloadLinearLayout(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )

        // Adapter for popular tv shows
        binding.popularTvRv.adapter = PreviewShowAdapter({
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCategoryMovieListFragment(
                    R.string.popular_tv_show_list_contentDesc, ShowCategoryIndex.POPULAR_TV_SHOWS
                )
            )
        }, {
            navigateToDetailMovie(it)
        })
        binding.popularTvRv.layoutManager = PreloadLinearLayout(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )

        // Adapter for on air tv shows
        binding.onAirPopularTvRv.adapter = PreviewShowAdapter({
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCategoryMovieListFragment(
                    R.string.now_airing_tv_show_list_contentDesc, ShowCategoryIndex.ON_AIR_TV_SHOWS
                )
            )
        }, {
            navigateToDetailMovie(it)
        })
        binding.onAirPopularTvRv.layoutManager = PreloadLinearLayout(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
    }


    private fun initiatePlaceHolderAdapter() {
        binding.popularMoviesPlaceholder.shimmerPlaceholder.adapter = PlaceHolderAdapter()
        binding.nowPlayingMoviesPlaceholder.shimmerPlaceholder.adapter = PlaceHolderAdapter()
        binding.popularTvPlaceholder.shimmerPlaceholder.adapter = PlaceHolderAdapter()
        binding.onAirTvPlaceholder.shimmerPlaceholder.adapter = PlaceHolderAdapter()
    }

    private fun navigateToDetailMovie(showItem: ShowResult) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.DETAIL_KEY, showItem)
        startActivity(intent)
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