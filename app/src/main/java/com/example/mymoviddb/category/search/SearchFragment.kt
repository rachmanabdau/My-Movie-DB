package com.example.mymoviddb.category.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mymoviddb.adapters.CategoryShowAdapter
import com.example.mymoviddb.adapters.PlaceHolderAdapter
import com.example.mymoviddb.category.movie.StateAdapter
import com.example.mymoviddb.core.model.ShowResult
import com.example.mymoviddb.databinding.FragmentSearchBinding
import com.example.mymoviddb.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    @Inject
    lateinit var showAdapter: CategoryShowAdapter

    private val searchViewModel by viewModels<SearchViewModel>()

    private val resultArgs by lazy {
        SearchFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        observeSearchMovies()

        // Get the SearchView and set the searchable configuration
        val searchManager =
            requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView.apply {
            maxWidth = Integer.MAX_VALUE
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
            isFocusable = true
            requestFocusFromTouch()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotBlank()) {
                    searchViewModel.searchShow(resultArgs.searchID, query)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun observeSearchMovies() {
        initAdapter()
        searchViewModel.showPageData.observe(viewLifecycleOwner) {
            showAdapter.submitData(lifecycle, it)
        }
    }

    private fun initAdapter() {
        binding.shimmerPlaceholderSearchAct.shimmerPlaceholder.apply {
            adapter = PlaceHolderAdapter()
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        // when id is to earch movie set movie adapter
        showAdapter.apply {
            doesNotNeedAuthentication()
            setItemClick { setIntentDetail(it) }
            lifecycleScope.launch {
                loadStateFlow.collectLatest {
                    handleLoadState(it.refresh) { retry() }
                }
            }
        }

        binding.searchShowsRv.adapter = showAdapter.apply {
            withLoadStateHeaderAndFooter(
                header = StateAdapter(showAdapter::retry),
                footer = StateAdapter(showAdapter::retry)
            )
        }
    }

    private fun setIntentDetail(showItem: ShowResult) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.DETAIL_KEY, showItem)
        startActivity(intent)
    }

    private fun handleLoadState(state: LoadState, retry: () -> Unit) {
        // show shimmer place holder when in loading state
        binding.shimmerPlaceholderSearchAct.root.isVisible =
            state is LoadState.Loading
        // show error message and try agian button when in error state
        binding.errorLayout.root.isVisible = state is LoadState.Error
        binding.errorLayout.tryAgainButton.setOnClickListener {
            retry()
        }
    }
}