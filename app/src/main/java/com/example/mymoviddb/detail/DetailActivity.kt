package com.example.mymoviddb.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.R
import com.example.mymoviddb.adapters.MoviesAdapter
import com.example.mymoviddb.adapters.TVAdapter
import com.example.mymoviddb.databinding.ActivityDetailBinding
import com.example.mymoviddb.home.PreloadLinearLayout
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.EventObserver
import com.example.mymoviddb.utils.LoginState
import com.example.mymoviddb.utils.PreferenceUtil
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var recommendationMoviesAdapter: MoviesAdapter

    private lateinit var similarMoviesAdapter: MoviesAdapter

    private lateinit var recommendationTVAdapter: TVAdapter

    private lateinit var similarTVAdapter: TVAdapter

    private val detailViewModel by viewModels<DetailViewModel>()

    private val args by navArgs<DetailActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.lifecycleOwner = this
        binding.detailViewModel = detailViewModel

        // determine show type from intent (navigation from search activity) or from bundle
        binding.showType = if (args.loadDetailId != 0) {
            args.loadDetailId
        } else {
            intent.getIntExtra(DETAIL_KEY, 0)
        }

        // determine show id from intent (navigation from search activity) or from bundle
        binding.showId = (if (args.showId != 0L) {
            args.showId
        } else {
            intent.getLongExtra(SHOW_ID_KEY, 0L)
        })

        detailViewModel.getShowDetail(binding.showId as Long, binding.showType as Int)

        // Show snack bar message from add/remove favourite (either succeed or failed)
        // Show snack bar message from add to/remove from watch list  (either succeed or failed)
        detailViewModel.showSnackbarMessage.observe(this, EventObserver {
            Snackbar.make(binding.root, getString(it), Snackbar.LENGTH_SHORT).show()
        })

        setupToolbar()
        setupFAB(binding.showId ?: 0L, binding.showType as Int)
        loadData(binding.showId ?: 0L, binding.showType as Int)
    }

    override fun onResume() {
        super.onResume()

        val userState = PreferenceUtil.getAuthState(this)

        if (userState == LoginState.AS_USER.stateId) {
            if (binding.showType as Int == DETAIL_MOVIE) {
                val userSessionId = PreferenceUtil.readUserSession(this)
                detailViewModel.getMovieAccountState(
                    binding.showId ?: 0L, userSessionId
                )
            } else {
                val userSessionId = PreferenceUtil.readUserSession(this)
                detailViewModel.getTVAccountState(
                    binding.showId ?: 0L, userSessionId
                )
            }
        }
    }

    companion object {
        const val DETAIL_KEY = "com.example.mymoviddb.detail.DetailActivity.DETAIL_KEY"
        const val SHOW_ID_KEY = "com.example.mymoviddb.detail.DetailActivity.SHOW_ID_KEY"
        const val DETAIL_MOVIE = 1
        const val DETAIL_TV = 2
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupFAB(showId: Long, showType: Int) {
        val state = PreferenceUtil.getAuthState(this)
        val sessionId = PreferenceUtil.readUserSession(this)
        val userId = PreferenceUtil.readAccountId(this)
        binding.showFAB = state != LoginState.AS_GUEST.stateId

        // Button click listener for add to favourite
        binding.favouriteBtnDetail.setOnClickListener {
            detailViewModel.markAsFavorite(userId, sessionId, showId, showType)
        }

        binding.addToWatchlistBtnDetail.setOnClickListener {
            detailViewModel.addToWatchList(userId, sessionId, showId, showType)
        }
    }

    private fun loadData(showId: Long, showType: Int) {

        initAdapter(showId, showType)

        if (binding.showType as Int == DETAIL_MOVIE) {
            // observe movie detail and set text for recommendation and similar movies
            observeMoviesDetail(
                binding.showId ?: 0L
            )
            binding.recommendationLabelDetail.text =
                getString(R.string.recommendation_detail_label, getString(R.string.movies_label))
            binding.similarLabelDetail.text =
                getString(R.string.similar_detail_label, getString(R.string.movies_label))
        } else {
            // observe tv show detail and set text for recommendation and similar tv shows
            observeTVDetail(
                binding.showId ?: 0L
            )
            binding.recommendationLabelDetail.text =
                getString(R.string.recommendation_detail_label, getString(R.string.tv_shows_label))
            binding.similarLabelDetail.text =
                getString(R.string.similar_detail_label, getString(R.string.tv_shows_label))
        }

    }

    private fun initAdapter(showId: Long, showType: Int) {
        if (showType == DETAIL_MOVIE) {
            // Adapter for recommendation movies
            recommendationMoviesAdapter = MoviesAdapter({
                detailViewModel.getRecommendationMovies(showId)
            }, {
                setIntentDetail(it, showType)
            }, false)

            binding.recommendtaionDetailRv.adapter = recommendationMoviesAdapter
            // layout manager for recommendation movies
            binding.recommendtaionDetailRv.layoutManager =
                PreloadLinearLayout(this, RecyclerView.HORIZONTAL, false)

            // Adapter for similar movies
            similarMoviesAdapter = MoviesAdapter({
                detailViewModel.getSimilarMovies(showId)
            }, {
                setIntentDetail(it, showType)
            }, false)

            binding.similarDetailRv.adapter = similarMoviesAdapter
            // layout manager for similar movies
            binding.similarDetailRv.layoutManager =
                PreloadLinearLayout(this, RecyclerView.HORIZONTAL, false)
        } else {

            // Adapter for recommendation tv show
            recommendationTVAdapter = TVAdapter({
                detailViewModel.getRecommendationTVShows(showId)
            }, {
                setIntentDetail(it, showType)
            }, false)

            binding.recommendtaionDetailRv.adapter = recommendationTVAdapter
            // layout manager for recommendation tv shows
            binding.recommendtaionDetailRv.layoutManager =
                PreloadLinearLayout(this, RecyclerView.HORIZONTAL, false)

            // Adapter for similar tv shows
            similarTVAdapter = TVAdapter({
                detailViewModel.getSimilarTVShows(showId)
            }, {
                setIntentDetail(it, showType)
            }, false)

            binding.similarDetailRv.adapter = similarTVAdapter
            // layout manager for similar tv shows
            binding.similarDetailRv.layoutManager =
                PreloadLinearLayout(this, RecyclerView.HORIZONTAL, false)
        }
    }

    // Navigation for recommendation and similar shows item to detail activity
    private fun setIntentDetail(showId: Long, detailKey: Int) {
        intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DETAIL_KEY, detailKey)
        intent.putExtra(SHOW_ID_KEY, showId)
        startActivity(intent)
    }

    private fun observeMoviesDetail(showId: Long) {
        detailViewModel.movieDetail.observe(this) {
            detailViewModel.determineDetailResult(it)
            if (it is Result.Success) {
                it.data?.apply {
                    binding.detailToolbar.titleCustom.text = title
                    binding.detailToolbar.titleCustom.visibility = View.VISIBLE
                }
                detailViewModel.getRecommendationMovies(showId)
                detailViewModel.getSimilarMovies(showId)
            } else if (it is Result.Error) {
                binding.errorDetail.errorMessage.text = it.exception.localizedMessage
                binding.errorDetail.tryAgainButton.setOnClickListener {
                    detailViewModel.getMovieDetail(showId)
                }
            }
        }

        detailViewModel.recommendationMovies.observe(this) {
            when (it) {
                is Result.Success -> {
                    recommendationMoviesAdapter.submitList(it.data?.results)

                    if (it.data?.results.isNullOrEmpty()) {
                        binding.recommendationContainer.visibility = View.GONE
                    }
                }

                is Result.Error -> {
                    binding.errorRecommendationDetail.tryAgainButton.setOnClickListener {
                        detailViewModel.getRecommendationMovies(showId)
                    }
                }
            }

            binding.errorRecommendationDetail.root.visibility =
                if (it !is Result.Success) View.VISIBLE else View.GONE
            binding.errorRecommendationDetail.retryLoading.visibility =
                if (it is Result.Loading) View.VISIBLE else View.GONE
            binding.errorRecommendationDetail.errorMessage.visibility =
                if (it is Result.Error) View.VISIBLE else View.GONE
            binding.errorRecommendationDetail.tryAgainButton.visibility =
                if (it is Result.Error) View.VISIBLE else View.GONE
        }

        detailViewModel.similarMovies.observe(this) {
            when (it) {
                is Result.Success -> {
                    similarMoviesAdapter.submitList(it.data?.results)

                    if (it.data?.results.isNullOrEmpty()) {
                        binding.similarContainer.visibility = View.GONE
                    }
                }

                is Result.Error -> {
                    binding.errorSimilarDetail.errorMessage.text = it.exception.localizedMessage
                    binding.errorSimilarDetail.tryAgainButton.setOnClickListener {
                        detailViewModel.getSimilarMovies(showId)
                    }
                }
            }

            binding.errorRecommendationDetail.root.visibility =
                if (it !is Result.Success) View.VISIBLE else View.GONE
            binding.errorRecommendationDetail.retryLoading.visibility =
                if (it is Result.Loading) View.VISIBLE else View.GONE
            binding.errorRecommendationDetail.errorMessage.visibility =
                if (it is Result.Error) View.VISIBLE else View.GONE
            binding.errorRecommendationDetail.tryAgainButton.visibility =
                if (it is Result.Error) View.VISIBLE else View.GONE
        }
    }

    private fun observeTVDetail(showId: Long) {

        detailViewModel.tvDetail.observe(this) {
            detailViewModel.determineDetailResult(it)
            if (it is Result.Success) {
                binding.detailToolbar.titleCustom.visibility = View.VISIBLE
                it.data?.apply {
                    binding.detailToolbar.titleCustom.text = name
                }
                detailViewModel.getRecommendationTVShows(showId)
                detailViewModel.getSimilarTVShows(showId)
            } else if (it is Result.Error) {
                binding.errorDetail.errorMessage.text = it.exception.localizedMessage
                binding.errorDetail.tryAgainButton.setOnClickListener {
                    detailViewModel.getTVDetail(showId)
                }
            }
        }

        detailViewModel.recommendationTVShows.observe(this) {
            when (it) {
                is Result.Success -> {
                    recommendationTVAdapter.submitList(it.data?.results)

                    if (it.data?.results.isNullOrEmpty()) {
                        binding.recommendationContainer.visibility = View.GONE
                    }
                }

                is Result.Error -> {
                    binding.errorRecommendationDetail.errorMessage.text =
                        it.exception.localizedMessage
                    binding.errorRecommendationDetail.tryAgainButton.setOnClickListener {
                        detailViewModel.getRecommendationTVShows(showId)
                    }
                }
            }

            binding.errorRecommendationDetail.root.visibility =
                if (it !is Result.Success) View.VISIBLE else View.GONE
            binding.errorRecommendationDetail.retryLoading.visibility =
                if (it is Result.Loading) View.VISIBLE else View.GONE
            binding.errorRecommendationDetail.errorMessage.visibility =
                if (it is Result.Error) View.VISIBLE else View.GONE
            binding.errorRecommendationDetail.tryAgainButton.visibility =
                if (it is Result.Error) View.VISIBLE else View.GONE
        }

        detailViewModel.similarTVShows.observe(this) {
            when (it) {
                is Result.Success -> {
                    similarTVAdapter.submitList(it.data?.results)

                    if (it.data?.results.isNullOrEmpty()) {
                        binding.similarContainer.visibility = View.GONE
                    }
                }

                is Result.Error -> {
                    binding.errorSimilarDetail.errorMessage.text = it.exception.localizedMessage
                    binding.errorSimilarDetail.tryAgainButton.setOnClickListener {
                        detailViewModel.getSimilarTVShows(showId)
                    }
                }
            }

            binding.errorSimilarDetail.root.visibility =
                if (it !is Result.Success) View.VISIBLE else View.GONE
            binding.errorSimilarDetail.retryLoading.visibility =
                if (it is Result.Loading) View.VISIBLE else View.GONE
            binding.errorSimilarDetail.errorMessage.visibility =
                if (it is Result.Error) View.VISIBLE else View.GONE
            binding.errorSimilarDetail.tryAgainButton.visibility =
                if (it is Result.Error) View.VISIBLE else View.GONE
        }
    }

    private fun setupToolbar() {
        // my_child_toolbar is defined in the layout file
        setSupportActionBar(binding.detailToolbar.toolbar)
        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // this code make marquee on text view works
        binding.detailToolbar.titleCustom.isSelected = true
    }
}