package com.example.mymoviddb.feature.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mymoviddb.adapters.CategoryShowAdapter
import com.example.mymoviddb.adapters.PlaceHolderAdapter
import com.example.mymoviddb.category.movie.StateAdapter
import com.example.mymoviddb.core.model.ShowResult
import com.example.mymoviddb.feature.search.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
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

    private var firstInitialize = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        //binding.searchToolbar.setupWithNavController(findNavController())
        observeSearchMovies()
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return binding.root
    }

    // Setup searchview
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val themedCntext = (requireActivity() as AppCompatActivity).supportActionBar?.themedContext
        themedCntext?.apply {
            menu.clear()
            inflater.inflate(R.menu.search_menu, menu)
            val item: MenuItem = menu.findItem(R.id.action_search)
            val searchView = SearchView(this)
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            item.setActionView(searchView)
            searchView.apply {
                setIconifiedByDefault(false)
                maxWidth = Integer.MAX_VALUE
                setPaddingRelative(0, 0, 0, 0)
                isFocusable = true
                requestFocusFromTouch()
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
            }
        }
    }

    private fun observeSearchMovies() {
        initAdapter()
        searchViewModel.showPageData.observe(viewLifecycleOwner) {
            showAdapter.submitData(lifecycle, it)
            firstInitialize = false
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
                    showEmptyResult(this@apply)
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
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailGraph(showItem)
        )
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

    private fun showEmptyResult(adapter: CategoryShowAdapter) {
        Timber.d(adapter.itemCount.toString())
        Timber.d((adapter.itemCount == 0 && !firstInitialize).toString())
        if (adapter.itemCount == 0 && !firstInitialize) {
            binding.errorLayout.root.isVisible = true
            binding.errorLayout.tryAgainButton.isVisible = false
            binding.errorLayout.errorMessage.text = "Not found"
        }
    }
}