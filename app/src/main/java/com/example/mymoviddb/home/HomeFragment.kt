package com.example.mymoviddb.home

import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.mymoviddb.R
import com.example.mymoviddb.adapters.PlaceHolderAdapter
import com.example.mymoviddb.adapters.PreviewShowAdapter
import com.example.mymoviddb.core.PreloadLinearLayout
import com.example.mymoviddb.core.ShowCategoryIndex
import com.example.mymoviddb.core.model.ShowResult
import com.example.mymoviddb.core.utils.EventObserver
import com.example.mymoviddb.core.utils.preference.LoginState
import com.example.mymoviddb.core.utils.preference.Preference
import com.example.mymoviddb.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var popularMoviesAdapter: PreviewShowAdapter

    @Inject
    lateinit var nowPlayingMoviesAdapter: PreviewShowAdapter

    @Inject
    lateinit var popularTvShowsAdapter: PreviewShowAdapter

    @Inject
    lateinit var onAirTvShowsAdapter: PreviewShowAdapter

    @Inject
    lateinit var userPreference: Preference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        launchLoginFragment()
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        homeViewModel.populateData()
        binding.homeViewModel = homeViewModel

        setHasOptionsMenu(true)
        initializeAdapter()
        setClickListener()
        setUpBackPressed()

        homeViewModel.snackbarMessage.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) ||
                super.onOptionsItemSelected(item)
    }

    private fun launchLoginFragment() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData(
            loginStateKey,
            userPreference.getAuthState()
        )?.observe(viewLifecycleOwner) {
            if (it == LoginState.NOT_LOGIN.stateId) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                )
            }
        }
    }

    private fun initializeAdapter() {
        initiatePlaceHolderAdapter()
        setupPopularMovieList()
        setupNowPlayingMovieList()
        setupPopularTvShowList()
        setupOnAirTvShowList()
    }

    private fun initiatePlaceHolderAdapter() {
        binding.popularMoviesPlaceholder.shimmerPlaceholder.adapter = PlaceHolderAdapter()
        binding.nowPlayingMoviesPlaceholder.shimmerPlaceholder.adapter = PlaceHolderAdapter()
        binding.popularTvPlaceholder.shimmerPlaceholder.adapter = PlaceHolderAdapter()
        binding.onAirTvPlaceholder.shimmerPlaceholder.adapter = PlaceHolderAdapter()
    }

    private fun setupPopularMovieList() {
        binding.popularMovieRv.adapter = popularMoviesAdapter
            .showLoadMore(true)
            .setLoadmoreClick { navigateToCategoryPopularMovies() }
            .setNavigationToDetail { navigateToDetailShow(it) }

        binding.popularMovieRv.layoutManager = PreloadLinearLayout(requireContext())
    }

    private fun navigateToCategoryPopularMovies() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryGraph(
                R.string.popular_movie_list_contentDesc, ShowCategoryIndex.POPULAR_MOVIES
            )
        )
    }

    private fun setupNowPlayingMovieList() {
        binding.nowPlayingMovieRv.adapter = nowPlayingMoviesAdapter
            .showLoadMore(true)
            .setLoadmoreClick { navigateToCategoryNowPlayingMovies() }
            .setNavigationToDetail { navigateToDetailShow(it) }

        binding.nowPlayingMovieRv.layoutManager = PreloadLinearLayout(requireContext())
    }

    private fun navigateToCategoryNowPlayingMovies() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryGraph(
                R.string.now_playing_movie_list_contentDesc,
                ShowCategoryIndex.NOW_PLAYING_MOVIES
            )
        )
    }

    private fun setupPopularTvShowList() {
        binding.popularTvRv.adapter = popularTvShowsAdapter
            .showLoadMore(true)
            .setLoadmoreClick { navigateToCategoryPopularTvShows() }
            .setNavigationToDetail { navigateToDetailShow(it) }

        binding.popularTvRv.layoutManager = PreloadLinearLayout(requireContext())
    }

    private fun navigateToCategoryPopularTvShows() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryGraph(
                R.string.popular_tv_show_list_contentDesc, ShowCategoryIndex.POPULAR_TV_SHOWS
            )
        )
    }

    private fun setupOnAirTvShowList() {
        binding.onAirPopularTvRv.adapter = onAirTvShowsAdapter
            .showLoadMore(true)
            .setLoadmoreClick { navigateToCategoryOnAirTvShows() }
            .setNavigationToDetail { navigateToDetailShow(it) }

        binding.onAirPopularTvRv.layoutManager = PreloadLinearLayout(requireContext())
    }

    private fun navigateToCategoryOnAirTvShows() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryGraph(
                R.string.now_airing_tv_show_list_contentDesc, ShowCategoryIndex.ON_AIR_TV_SHOWS
            )
        )
    }

    private fun navigateToDetailShow(showItem: ShowResult) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailGraph(
                showItem
            )
        )
    }

    private fun setClickListener() {
        setPopularMoviesReloadButton()
        setNowPlayingMoviesReloadButton()
        setPopularTvShowsReloadButton()
        setOnAirTvShowsReloadButton()
    }

    private fun setPopularMoviesReloadButton() {
        binding.errorPopularMoviesMessage.tryAgainButton.setOnClickListener {
            binding.errorPopularMoviesMessage.tryAgainButton.isEnabled = false
            homeViewModel.getPopularMovieList()
            binding.errorPopularMoviesMessage.tryAgainButton.isEnabled = true
        }
    }

    private fun setNowPlayingMoviesReloadButton() {
        binding.errorNowPlayingMoviesMessage.tryAgainButton.setOnClickListener {
            binding.errorNowPlayingMoviesMessage.tryAgainButton.isEnabled = false
            homeViewModel.getNowPlayingMovieList()
            binding.errorNowPlayingMoviesMessage.tryAgainButton.isEnabled = true
        }
    }

    private fun setPopularTvShowsReloadButton() {
        binding.errorPopularTvMessage.tryAgainButton.setOnClickListener {
            binding.errorPopularTvMessage.tryAgainButton.isEnabled = false
            homeViewModel.getPopularTVList()
            binding.errorPopularTvMessage.tryAgainButton.isEnabled = true
        }
    }

    private fun setOnAirTvShowsReloadButton() {
        binding.errorOnAirTvMessage.tryAgainButton.setOnClickListener {
            binding.errorOnAirTvMessage.tryAgainButton.isEnabled = false
            homeViewModel.getonAirTVList()
            binding.errorOnAirTvMessage.tryAgainButton.isEnabled = true
        }
    }

    private fun setUpBackPressed() {
        // navigate to
        if (homeViewModel.userIsLoginAsGuest()) {
            requireActivity().onBackPressedDispatcher.addCallback(this) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                )
            }
        }
    }

    companion object {
        const val loginStateKey = "com.example.mymoviddb.home.HomeFragment.loginStateKey"
    }
}