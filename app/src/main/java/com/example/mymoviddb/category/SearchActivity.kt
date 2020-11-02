package com.example.mymoviddb.category

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mymoviddb.R
import com.example.mymoviddb.SearchDialogChooser
import com.example.mymoviddb.adapters.MovieListAdapter
import com.example.mymoviddb.category.movie.MovieDataSource
import com.example.mymoviddb.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private val searchViewModel by viewModels<SearchViewModel>()
    private var id = 0
    private var counter = 0
    var firstInitialize = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.lifecycleOwner = this
        id = intent.getIntExtra(SearchDialogChooser.SEARCH_ID, 0)
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
                    if (id == SEARCH_MOVIES) {
                        val adapter = MovieListAdapter { /*searchViewModel.retrySearchMovies() */ }
                        binding.moviesRv.adapter = adapter
                        observeSearchMovies(adapter)

                        MovieDataSource.MOVIE_CATEGORY_ID = MovieDataSource.SEARCH_MOVIES
                        searchViewModel.searchTitle(query)
                    }
                }
            }
        }
    }

    private fun observeSearchMovies(adapter: MovieListAdapter) {

        /*searchViewModel.resultMovie.observe(this, {
            adapter.setState(it)

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
        })*/

        searchViewModel.movieList.observe(this, {
            adapter.submitList(it)
        })/*

        binding.errorLayout.tryAgainButton.setOnClickListener {
            searchViewModel.retrySearchMovies()
        }*/
    }

    companion object {
        const val SEARCH_MOVIES = 31
        const val SEARCH_TV = 32
    }

}