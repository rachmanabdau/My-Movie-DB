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
import com.example.mymoviddb.core.ResultHandler
import com.example.mymoviddb.core.ShowCategoryIndex
import com.example.mymoviddb.core.model.ShowResult
import com.example.mymoviddb.databinding.FragmentWatchListTvBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WatchListTVFragment : Fragment(), ResultHandler {

    private lateinit var binding: FragmentWatchListTvBinding

    private val watchListTVViewModel by viewModels<AccountShowViewModel>()

    @Inject
    lateinit var adapter: CategoryShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchListTvBinding.inflate(inflater, container, false)
        setupView()

        return binding.root
    }

    private fun setupView() {
        binding.lifecycleOwner = this
        binding.watchlistTvErrorLayout.tryAgainButton.visibility = View.GONE

        setupAdapter()
        binding.watchlistTvSwipeRefresh.setOnRefreshListener {
            watchListTVViewModel.getShowList(ShowCategoryIndex.WATCHLIST_TV_SHOWS)
        }

        watchListTVViewModel.getShowList(ShowCategoryIndex.WATCHLIST_TV_SHOWS)
        watchListTVViewModel.accountShowList.observe(viewLifecycleOwner) {
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
        binding.watchlistTvRv.adapter = adapter
    }

    override fun setRetryButton(adapter: CategoryShowAdapter) {
        binding.watchlistTvErrorLayout.tryAgainButton.setOnClickListener {
            adapter.retry()
        }
    }

    override fun navigateToDetailMovie(showItem: ShowResult) {
        findNavController().navigate(
            WatchListTVFragmentDirections.actionWatchListTVFragmentToDetailGraph(
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
        binding.watchlistTvSwipeRefresh.isRefreshing = isRefreshing
        // show error message and try again button when in error state
        binding.watchlistTvErrorLayout.root.isVisible = (isError || isResultEmpty)
        showPlaceholderMessage(isResultEmpty)
    }

    override fun showPlaceholderMessage(isItemEmpty: Boolean) {
        if (isItemEmpty) {
            binding.watchlistTvErrorLayout.errorMessage.text =
                getString(R.string.empty_watchlist_tv_show)
        }
    }

}