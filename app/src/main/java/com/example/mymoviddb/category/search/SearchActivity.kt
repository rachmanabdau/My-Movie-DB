package com.example.mymoviddb.category.search

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.example.mymoviddb.R
import com.example.mymoviddb.adapters.MovieListAdapter
import com.example.mymoviddb.adapters.TVListAdapter
import com.example.mymoviddb.category.movie.MovieDataSource
import com.example.mymoviddb.category.tv.TVDataSource
import com.example.mymoviddb.databinding.ActivitySearchBinding
import com.example.mymoviddb.model.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    lateinit var movieAdapter: MovieListAdapter

    lateinit var tvAdapter: TVListAdapter

    private val searchViewModel by viewModels<SearchViewModel>()

    private val args by navArgs<SearchActivityArgs>()

    private var id = 0

    private var firstInitialize = true

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
            // Assumes current activity is the searchable activity
            setSearchableInfo(
                searchManager.getSearchableInfo(
                    ComponentName(
                        this@SearchActivity,
                        SearchActivity::class.java
                    )
                )
            )
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                if (query.isNotBlank()) {
                    if (id == MovieDataSource.SEARCH_MOVIES) {
                        binding.tvRv.visibility = View.GONE
                        movieAdapter.submitList(null)
                        movieAdapter.notifyDataSetChanged()

                        MovieDataSource.MOVIE_CATEGORY_ID = MovieDataSource.SEARCH_MOVIES
                        searchViewModel.searchMovieTitle(query.trim())
                    } else if (id == TVDataSource.SEARCH_TV) {
                        binding.moviesRv.visibility = View.GONE
                        tvAdapter.submitList(null)

                        TVDataSource.TV_CATEGORY_ID = TVDataSource.SEARCH_TV
                        searchViewModel.searchTvTitle(query.trim())
                    }
                }
            }
        }
    }

    private fun observeSearchMovies() {

        searchViewModel.movieList.observe(this, {
            movieAdapter.submitList(it)
        })

        searchViewModel.resultMovie.observe(this, {
            movieAdapter.setState(it)

            if (it is Result.Error && firstInitialize) {
                val message = it.exception.localizedMessage ?: "Unknown error has occured"
                binding.errorLayout.errorMessage.text = message
                binding.errorLayout.root.visibility = View.VISIBLE
                binding.loadingBar.visibility = View.GONE
            } else if (it is Result.Success) {
                firstInitialize = false
                binding.errorLayout.root.visibility = View.GONE
                binding.loadingBar.visibility = View.GONE
            } else if (it is Result.Loading && firstInitialize) {
                binding.loadingBar.visibility = View.VISIBLE
            }
        })

        binding.errorLayout.tryAgainButton.setOnClickListener {
            searchViewModel.retrySearchMovies()
        }
    }

    private fun observeSearchTV() {

        searchViewModel.tvList.observe(this, {
            tvAdapter.submitList(it)
        })

        searchViewModel.resultTV.observe(this, {
            tvAdapter.setState(it)

            if (it is Result.Error && firstInitialize) {
                val message = it.exception.localizedMessage ?: "Unknown error has occured"
                binding.errorLayout.errorMessage.text = message
                binding.errorLayout.root.visibility = View.VISIBLE
                binding.loadingBar.visibility = View.GONE
            } else if (it is Result.Success) {
                firstInitialize = false
                binding.errorLayout.root.visibility = View.GONE
                binding.loadingBar.visibility = View.GONE
            } else if (it is Result.Loading && firstInitialize) {
                binding.loadingBar.visibility = View.VISIBLE
            }
        })

        binding.errorLayout.tryAgainButton.setOnClickListener {
            searchViewModel.retrySearchTV()
        }
    }

    private fun initAdapter(id: Int) {
        if (id == 31) {
            movieAdapter = MovieListAdapter { searchViewModel.retrySearchMovies() }
            binding.moviesRv.adapter = movieAdapter
            observeSearchMovies()
        } else {
            tvAdapter = TVListAdapter { searchViewModel.retrySearchTV() }
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
}