package com.example.mymoviddb.category.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mymoviddb.R
import com.example.mymoviddb.adapters.MovieListAdapterV3
import com.example.mymoviddb.adapters.PlaceHolderAdapter
import com.example.mymoviddb.category.movie.MovieDataSource
import com.example.mymoviddb.category.movie.StateAdapter
import com.example.mymoviddb.category.tv.TVDataSource
import com.example.mymoviddb.category.tv.TVListAdapterV3
import com.example.mymoviddb.databinding.ActivitySearchBinding
import com.example.mymoviddb.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private lateinit var movieAdapter: MovieListAdapterV3

    private lateinit var tvAdapter: TVListAdapterV3

    private val searchViewModel by viewModels<SearchViewModel>()

    private val args by navArgs<SearchActivityArgs>()

    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.lifecycleOwner = this
        id = args.searchID

        setupToolbar()
        initAdapter(id)
        handleIntent(intent)

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView.apply {
            maxWidth = Integer.MAX_VALUE
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
            isFocusable = true
            requestFocusFromTouch()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                if (query.isNotBlank()) {
                    if (id == MovieDataSource.SEARCH_MOVIES) {
                        binding.tvRv.visibility = View.GONE

                        searchViewModel.searchMovieData(
                            MovieDataSource.SEARCH_MOVIES,
                            query.trim()
                        )
                    } else if (id == TVDataSource.SEARCH_TV) {
                        binding.moviesRv.visibility = View.GONE

                        searchViewModel.searchTVData(TVDataSource.SEARCH_TV, query.trim())
                    }
                }
                binding.searchView.setQuery(query, false)
            }
        }
    }

    private fun observeSearchMovies() {

        searchViewModel.moviePageData.observe(this, {
            lifecycleScope.launch {
                movieAdapter.submitData(it)
            }
        })
    }

    private fun observeSearchTV() {
        searchViewModel.tvPageData.observe(this, {
            lifecycleScope.launch {
                tvAdapter.submitData(it)
            }
        })
    }

    private fun initAdapter(id: Int) {
        binding.shimmerPlaceholderSearchAct.shimmerPlaceholder.apply {
            adapter = PlaceHolderAdapter()
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        if (id == 31) {
            // when id is to earch movie set movie adapter
            movieAdapter = MovieListAdapterV3 {
                setIntentDetail(it, DetailActivity.DETAIL_MOVIE)
            }.apply {

                lifecycleScope.launch {
                    loadStateFlow.collectLatest {
                        handleLoadState(it.refresh) {
                            retry()
                        }
                    }
                }
            }

            binding.moviesRv.adapter = movieAdapter.apply {
                withLoadStateHeaderAndFooter(
                    header = StateAdapter(movieAdapter::retry),
                    footer = StateAdapter(movieAdapter::retry)
                )
            }

            observeSearchMovies()
        } else {
            // else set tv adapter
            tvAdapter = TVListAdapterV3 {
                setIntentDetail(it, DetailActivity.DETAIL_TV)
            }.apply {
                lifecycleScope.launch {
                    loadStateFlow.collectLatest {
                        handleLoadState(it.refresh) {
                            retry()
                        }
                    }
                }
            }

            binding.tvRv.adapter = tvAdapter
            observeSearchTV()
        }
    }

    private fun setupToolbar() {
        // my_child_toolbar is defined in the layout file
        setSupportActionBar(binding.searchToolbar)
        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setIntentDetail(showId: Long, detailKey: Int) {
        intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.DETAIL_KEY, detailKey)
        intent.putExtra(DetailActivity.SHOW_ID_KEY, showId)
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