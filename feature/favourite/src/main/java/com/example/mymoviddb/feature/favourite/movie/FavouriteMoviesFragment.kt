package com.example.mymoviddb.feature.favourite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.map
import com.example.mymoviddb.adapters.CategoryShowAdapter
import com.example.mymoviddb.core.ResultHandler
import com.example.mymoviddb.core.model.ShowResult
import com.example.mymoviddb.feature.favourite.R
import com.example.mymoviddb.feature.favourite.databinding.FragmentFavouriteMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteMoviesFragment : Fragment(), ResultHandler {

    private lateinit var binding: FragmentFavouriteMoviesBinding

    private val favouriteViewModel by viewModels<FavouriteMovieViewModel>()

    @Inject
    lateinit var adapter: CategoryShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteMoviesBinding.inflate(inflater, container, false)
        setupView()

        return binding.root
    }

    private fun setupView() {
        binding.lifecycleOwner = this
        binding.favouriteErrorLayout.tryAgainButton.visibility = View.GONE

        setupAdapter()
        binding.favouriteRv.adapter = adapter
        binding.favouriteSwipeRefresh.setOnRefreshListener {
            favouriteViewModel.getFavouriteMovieList()
        }

        favouriteViewModel.getFavouriteMovieList()
        favouriteViewModel.favouriteMovieList.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(lifecycle, pagingData.map { it })
        }
    }

    private fun setupAdapter() {
        adapter.also { adapter ->
            adapter.setItemClick { showResult -> navigateToDetailMovie(showResult) }
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.loadStateFlow.collectLatest { loadState ->
                    Timber.d("loadState = ${loadState.refresh}")
                    setViewResult(loadState, adapter)
                    setRetryButton(adapter)
                }
            }
        }
    }

    override fun setRetryButton(adapter: CategoryShowAdapter) {
        binding.favouriteErrorLayout.tryAgainButton.setOnClickListener {
            adapter.retry()
        }
    }

    override fun navigateToDetailMovie(showItem: ShowResult) {
        findNavController().navigate(
            FavouriteMoviesFragmentDirections.actionFavouriteMoviesFragmentToDetailGraph(
                showItem
            )
        )
    }

    override fun setViewResult(
        loadState: CombinedLoadStates,
        favouriteAdapter: CategoryShowAdapter
    ) {
        val isRefreshing = loadState.refresh is LoadState.Loading
        val isError = loadState.refresh is LoadState.Error
        val isResultEmpty =
            loadState.refresh is LoadState.NotLoading && favouriteAdapter.itemCount == 0
        // show shimmer place holder when in loading state
        binding.favouriteSwipeRefresh.isRefreshing = isRefreshing
        // show error message and try agian button when in error state
        binding.favouriteErrorLayout.root.isVisible = (isError && isResultEmpty)
        showPlaceholderMessage(isResultEmpty)
    }

    override fun showPlaceholderMessage(isItemEmpty: Boolean) {
        if (isItemEmpty) {
            binding.favouriteErrorLayout.errorMessage.text =
                getString(R.string.empty_favourite_movie)
        }
    }

}