package com.example.mymoviddb.category.account.favourite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.mymoviddb.R
import com.example.mymoviddb.adapters.CategoryShowAdapter
import com.example.mymoviddb.category.ShowCategoryIndex
import com.example.mymoviddb.category.account.AccountShowViewModel
import com.example.mymoviddb.category.account.ResultHandler
import com.example.mymoviddb.databinding.FragmentFavouriteTvShowsBinding
import com.example.mymoviddb.detail.DetailActivity
import com.example.mymoviddb.model.ShowResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteTVShowsFragment : Fragment(), ResultHandler {

    private lateinit var binding: FragmentFavouriteTvShowsBinding

    private val favouriteViewModel by viewModels<AccountShowViewModel>()

    @Inject
    lateinit var favouriteTvShowAdapter: CategoryShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteTvShowsBinding.inflate(inflater, container, false)
        setupView()

        return binding.root
    }

    private fun setupView() {
        binding.lifecycleOwner = this
        binding.favouriteErrorLayout.tryAgainButton.visibility = View.GONE

        setupAdapter()
        binding.favouriteSwipeRefresh.setOnRefreshListener {
            favouriteViewModel.getShowList(ShowCategoryIndex.FAVOURITE_TV_SHOWS)
        }

        favouriteViewModel.getShowList(ShowCategoryIndex.FAVOURITE_TV_SHOWS)
        favouriteViewModel.accountShowList.observe(viewLifecycleOwner) {
            favouriteTvShowAdapter.submitData(lifecycle, it)
        }
    }

    private fun setupAdapter() {
        binding.favouriteRv.adapter = favouriteTvShowAdapter.also { adapter ->
            adapter.setItemClick { favouriteTvItem -> navigateToDetailMovie(favouriteTvItem) }
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.loadStateFlow.collectLatest { loadState ->
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
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.DETAIL_KEY, showItem)
        startActivity(intent)
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
        binding.favouriteErrorLayout.root.isVisible = (isError || isResultEmpty)
        showPlaceholderMessage(isResultEmpty)
    }

    override fun showPlaceholderMessage(isItemEmpty: Boolean) {
        if (isItemEmpty) {
            binding.favouriteErrorLayout.errorMessage.text =
                getString(R.string.empty_favourite_tv_show)
        }
    }

}