package com.example.mymoviddb.category.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the options menu from XML
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.searchable_menu)?.actionView as SearchView).apply {
            setMaxWidth(Integer.MAX_VALUE)
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
        }

        return true

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
                        tvAdapter.notifyDataSetChanged()

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

            when {
                // when result is error
                it is Result.Error && firstInitialize -> {
                    val message = it.exception.localizedMessage ?: "Unknown error has occured"
                    // show error message
                    binding.errorLayout.errorMessage.text = message
                    // show error layout visibility
                    binding.errorLayout.root.visibility = View.VISIBLE
                    // hide loading progressbar
                    binding.loadingBar.visibility = View.GONE
                }
                // when result is success
                it is Result.Success -> {
                    // hide try again button visibility
                    binding.errorLayout.tryAgainButton.visibility = View.GONE
                    // hide progress bar visibility
                    binding.loadingBar.visibility = View.GONE
                    // show data not found text when list is empty
                    binding.errorLayout.root.visibility =
                        if (it.data?.totalResults == 0) View.VISIBLE else View.GONE
                    binding.errorLayout.errorMessage.text = getString(R.string.movie_not_found)
                    // if list empty then first inital state still/return to true else false
                    firstInitialize = it.data?.totalResults == 0
                }
                // when fething data in process
                it is Result.Loading && firstInitialize -> {
                    // show loading bar inside error essage container (not from error mesage recyclerview)
                    binding.loadingBar.visibility = View.VISIBLE
                }
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

            when {
                it is Result.Error && firstInitialize -> {
                    val message = it.exception.localizedMessage ?: "Unknown error has occured"
                    // show error message
                    binding.errorLayout.errorMessage.text = message
                    // show error container
                    binding.errorLayout.root.visibility = View.VISIBLE
                    // hide progress bar
                    binding.loadingBar.visibility = View.GONE
                }
                it is Result.Success -> {
                    // hide try again button and loading bar ...
                    binding.errorLayout.tryAgainButton.visibility = View.GONE
                    binding.loadingBar.visibility = View.GONE
                    // ... but still show error message and error container visibility to show empty list message
                    binding.errorLayout.root.visibility =
                        if (it.data?.totalResults == 0) View.VISIBLE else View.GONE
                    binding.errorLayout.errorMessage.text = getString(R.string.tv_show_not_found)
                    // set first initial if list data empty
                    firstInitialize = it.data?.totalResults == 0
                }
                it is Result.Loading && firstInitialize -> {
                    // show progressbar in roor view (not in the recyclerview one)
                    binding.loadingBar.visibility = View.VISIBLE
                }
            }
        })

        binding.errorLayout.tryAgainButton.setOnClickListener {
            searchViewModel.retrySearchTV()
        }
    }

    private fun initAdapter(id: Int) {
        if (id == 31) {
            // when id is to earch movie set movie adapter
            movieAdapter = MovieListAdapter { searchViewModel.retrySearchMovies() }
            binding.moviesRv.adapter = movieAdapter
            observeSearchMovies()
        } else {
            // else set tv adapter
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