package com.example.mymoviddb.category.account.watchlist

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
import com.example.mymoviddb.R
import com.example.mymoviddb.adapters.CategoryShowAdapter
import com.example.mymoviddb.category.account.AccountShowViewModel
import com.example.mymoviddb.category.account.ResultHandler
import com.example.mymoviddb.core.ShowCategoryIndex
import com.example.mymoviddb.core.model.ShowResult
import com.example.mymoviddb.databinding.FragmentWatchListMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WatchListMovieFragment : Fragment(), ResultHandler {

    private lateinit var binding: FragmentWatchListMovieBinding

    private val watchListViewmodel by viewModels<AccountShowViewModel>()

    @Inject
    lateinit var adapter: CategoryShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWatchListMovieBinding.inflate(inflater, container, false)
        setupView()

        return binding.root
    }

    private fun setupView() {
        binding.lifecycleOwner = this
        binding.watchlistErrorLayout.tryAgainButton.visibility = View.GONE

        setupAdapter()
        binding.watchlistRv.adapter = adapter
        binding.watchlistSwipeRefresh.setOnRefreshListener {
            watchListViewmodel.getShowList(ShowCategoryIndex.WATCHLIST_MOVIES)
        }

        watchListViewmodel.getShowList(ShowCategoryIndex.WATCHLIST_MOVIES)
        watchListViewmodel.accountShowList.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun setupAdapter() {
        adapter.also { adapter ->
            adapter.setItemClick { showResult -> navigateToDetailMovie(showResult) }
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.loadStateFlow.collectLatest { loadState ->
                    setViewResult(loadState, adapter)
                    setRetryButton(adapter)
                }
            }
        }
    }

    override fun setRetryButton(adapter: CategoryShowAdapter) {
        binding.watchlistErrorLayout.tryAgainButton.setOnClickListener {
            adapter.retry()
        }
    }

    override fun navigateToDetailMovie(showItem: ShowResult) {
        findNavController().navigate(
            WatchListMovieFragmentDirections.actionWatchListMovieFragmentToDetailGraph(
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
        binding.watchlistSwipeRefresh.isRefreshing = isRefreshing
        // show error message and try agian button when in error state
        binding.watchlistErrorLayout.root.isVisible = (isError || isResultEmpty)
        showPlaceholderMessage(isResultEmpty)
    }

    override fun showPlaceholderMessage(isItemEmpty: Boolean) {
        if (isItemEmpty) {
            binding.watchlistErrorLayout.errorMessage.text =
                getString(R.string.empty_watchlist_movie)
        }
    }

}