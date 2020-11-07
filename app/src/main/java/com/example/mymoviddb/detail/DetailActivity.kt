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
import com.example.mymoviddb.model.MarkAsFavorite
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.LoginState
import com.example.mymoviddb.utils.PreferenceUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var recommendationMoviesAdapter: MoviesAdapter

    private lateinit var similarMoviesAdapter: MoviesAdapter

    private lateinit var recommendationTVAdapter: TVAdapter

    private lateinit var similarTVAdapter: TVAdapter

    private var favoriteState = false

    private val detailViewModel by viewModels<DetailViewModel>()

    private val args by navArgs<DetailActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        setupToolbar()
        showHideActionButton()
        loadData()
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

    private fun showHideActionButton() {
        val state = PreferenceUtil.getAuthState(this)
        binding.showFAB = state != LoginState.AS_GUEST.stateId
    }

    private fun initAdapter(showId: Long, loadId: Int) {
        if (loadId == DETAIL_MOVIE) {

            recommendationMoviesAdapter = MoviesAdapter({
                detailViewModel.getRecommendationMovies(showId)
            }, {
                setIntentDetail(it, loadId)
            }, false)

            binding.recommendtaionDetailRv.adapter = recommendationMoviesAdapter
            binding.recommendtaionDetailRv.layoutManager =
                PreloadLinearLayout(this, RecyclerView.HORIZONTAL, false)

            similarMoviesAdapter = MoviesAdapter({
                detailViewModel.getSimilarMovies(showId)
            }, {
                setIntentDetail(it, loadId)
            }, false)

            binding.similarDetailRv.adapter = similarMoviesAdapter
            binding.similarDetailRv.layoutManager =
                PreloadLinearLayout(this, RecyclerView.HORIZONTAL, false)
        } else {

            recommendationTVAdapter = TVAdapter({
                detailViewModel.getRecommendationTVShows(showId)
            }, {
                setIntentDetail(it, loadId)
            }, false)

            binding.recommendtaionDetailRv.adapter = recommendationTVAdapter
            binding.recommendtaionDetailRv.layoutManager =
                PreloadLinearLayout(this, RecyclerView.HORIZONTAL, false)

            similarTVAdapter = TVAdapter({
                detailViewModel.getSimilarTVShows(showId)
            }, {
                setIntentDetail(it, loadId)
            }, false)

            binding.similarDetailRv.adapter = similarTVAdapter
            binding.similarDetailRv.layoutManager =
                PreloadLinearLayout(this, RecyclerView.HORIZONTAL, false)
        }
    }

    private fun loadData() {
        var mediaType = ""
        val loadId = if (args.loadDetailId != 0) {
            args.loadDetailId
        } else {
            intent.getIntExtra(DETAIL_KEY, 0)
        }

        val showId = if (args.showId != 0L) {
            args.showId
        } else {
            intent.getLongExtra(SHOW_ID_KEY, 0L)
        }

        initAdapter(showId, loadId)

        val userState = PreferenceUtil.getAuthState(this)
        if (loadId == DETAIL_MOVIE) {
            mediaType = "movie"
            detailViewModel.getMovieDetail(showId)
            detailViewModel.getRecommendationMovies(showId)
            detailViewModel.getSimilarMovies(showId)

            if (userState == LoginState.AS_USER.stateId) {
                val userSessionId = PreferenceUtil.readUserSession(this)
                detailViewModel.getMovieAccountState(showId, userSessionId)
                observeAccountState()
            }

            observeMoviesDetail(showId)
            binding.recommendationLabelDetail.text =
                getString(R.string.recommendation_detail_label, getString(R.string.movies_label))
            binding.similarLabelDetail.text =
                getString(R.string.similar_detail_label, getString(R.string.movies_label))
        } else {
            mediaType = "tv"
            detailViewModel.getTVDetail(showId)
            detailViewModel.getRecommendationTVShows(showId)
            detailViewModel.getSimilarTVShows(showId)

            if (userState == LoginState.AS_USER.stateId) {
                val userSessionId = PreferenceUtil.readUserSession(this)
                detailViewModel.getTVAccountState(showId, userSessionId)
                observeAccountState()
            }

            observeTVDetail(showId)
            binding.recommendationLabelDetail.text =
                getString(R.string.recommendation_detail_label, getString(R.string.tv_shows_label))
            binding.similarLabelDetail.text =
                getString(R.string.similar_detail_label, getString(R.string.tv_shows_label))
        }

        binding.favouriteFabDetail.setOnClickListener {
            val sessionId = PreferenceUtil.readUserSession(this)

            val mediatype = MarkAsFavorite(favoriteState, showId, mediaType)
            val userId = PreferenceUtil.readAccountId(this)
            detailViewModel.markAsFavorite(userId, sessionId, mediatype)
        }
    }

    private fun setIntentDetail(showId: Long, detailKey: Int) {
        intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DETAIL_KEY, detailKey)
        intent.putExtra(SHOW_ID_KEY, showId)
        startActivity(intent)
    }

    private fun observeMoviesDetail(showId: Long) {
        detailViewModel.movieDetail.observe(this) {
            when (it) {
                is Result.Success -> {
                    binding.detailContainer.visibility = View.VISIBLE
                    binding.detailProgress.visibility = View.GONE
                    binding.errorDetail.root.visibility = View.GONE
                    binding.detailToolbar.titleCustom.visibility = View.VISIBLE
                    it.data?.apply {
                        binding.detailToolbar.titleCustom.text = title
                        binding.ratingDetail.text =
                            getString(R.string.rate_detail, (voteAverage * 10).toInt())
                        binding.releaseDateDetail.text =
                            getString(R.string.release_date_detail, releaseDate)

                        var genre = ""
                        for (i in genres) {
                            genre += "${i.name} "
                        }
                        binding.genre = genre
                        binding.overview = overview
                        binding.posterPath = posterPath
                    }
                }

                is Result.Loading -> {
                    binding.detailProgress.visibility = View.VISIBLE
                    binding.errorDetail.root.visibility = View.GONE
                    binding.detailContainer.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.errorDetail.root.visibility = View.VISIBLE
                    binding.detailProgress.visibility = View.GONE
                    binding.detailContainer.visibility = View.GONE
                    binding.errorDetail.errorMessage.text = it.exception.localizedMessage
                    binding.errorDetail.tryAgainButton.setOnClickListener {
                        detailViewModel.getMovieDetail(showId)
                    }
                }
            }
        }

        detailViewModel.recommendationMovies.observe(this) {
            when (it) {
                is Result.Success -> {
                    recommendationMoviesAdapter.submitList(it.data?.results)
                    binding.errorRecommendationDetail.root.visibility = View.GONE

                    if (it.data?.results.isNullOrEmpty()) {
                        binding.recommendationContainer.visibility = View.GONE
                    }
                }

                is Result.Loading -> {
                    binding.errorRecommendationDetail.root.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.retryLoading.visibility = View.GONE
                    binding.errorRecommendationDetail.errorMessage.visibility = View.GONE
                    binding.errorRecommendationDetail.tryAgainButton.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.errorRecommendationDetail.root.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.retryLoading.visibility = View.GONE
                    binding.errorRecommendationDetail.errorMessage.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.tryAgainButton.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.tryAgainButton.setOnClickListener {
                        detailViewModel.getRecommendationMovies(showId)
                    }
                }
            }
        }

        detailViewModel.similarMovies.observe(this) {
            when (it) {
                is Result.Success -> {
                    similarMoviesAdapter.submitList(it.data?.results)
                    binding.errorSimilarDetail.root.visibility = View.GONE

                    if (it.data?.results.isNullOrEmpty()) {
                        binding.similarContainer.visibility = View.GONE
                    }
                }

                is Result.Loading -> {
                    binding.errorSimilarDetail.root.visibility = View.VISIBLE
                    binding.errorSimilarDetail.retryLoading.visibility = View.VISIBLE
                    binding.errorSimilarDetail.errorMessage.visibility = View.GONE
                    binding.errorSimilarDetail.tryAgainButton.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.errorSimilarDetail.root.visibility = View.VISIBLE
                    binding.errorSimilarDetail.retryLoading.visibility = View.GONE
                    binding.errorSimilarDetail.errorMessage.visibility = View.VISIBLE
                    binding.errorSimilarDetail.tryAgainButton.visibility = View.VISIBLE
                    binding.errorSimilarDetail.errorMessage.text = it.exception.localizedMessage
                    binding.errorSimilarDetail.tryAgainButton.setOnClickListener {
                        detailViewModel.getRecommendationMovies(showId)
                    }
                }
            }
        }
    }

    private fun observeTVDetail(showId: Long) {

        detailViewModel.tvDetail.observe(this) {
            when (it) {
                is Result.Success -> {
                    binding.detailContainer.visibility = View.VISIBLE
                    binding.detailProgress.visibility = View.GONE
                    binding.errorDetail.root.visibility = View.GONE
                    binding.detailToolbar.titleCustom.visibility = View.VISIBLE
                    it.data?.apply {
                        binding.detailToolbar.titleCustom.text = name
                        binding.ratingDetail.text =
                            getString(R.string.rate_detail, (voteAverage * 10).toInt())
                        binding.releaseDateDetail.text =
                            getString(R.string.first_air_date_detail, firstAirDate)

                        var genre = ""
                        for (i in genres) {
                            genre += "${i.name} "
                        }
                        binding.genre = genre
                        binding.overview = overview
                        binding.posterPath = posterPath
                    }
                }

                is Result.Loading -> {
                    binding.detailProgress.visibility = View.VISIBLE
                    binding.errorDetail.root.visibility = View.GONE
                    binding.detailContainer.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.errorDetail.root.visibility = View.VISIBLE
                    binding.detailProgress.visibility = View.GONE
                    binding.detailContainer.visibility = View.GONE
                    binding.errorDetail.errorMessage.text = it.exception.localizedMessage
                    binding.errorDetail.tryAgainButton.setOnClickListener {
                        detailViewModel.getTVDetail(showId)
                    }
                }
            }
        }

        detailViewModel.recommendationTVShows.observe(this) {
            when (it) {
                is Result.Success -> {
                    recommendationTVAdapter.submitList(it.data?.results)
                    binding.errorRecommendationDetail.root.visibility = View.GONE

                    if (it.data?.results.isNullOrEmpty()) {
                        binding.recommendationContainer.visibility = View.GONE
                    }
                }

                is Result.Loading -> {
                    binding.errorRecommendationDetail.root.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.retryLoading.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.errorMessage.visibility = View.GONE
                    binding.errorRecommendationDetail.tryAgainButton.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.errorRecommendationDetail.root.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.retryLoading.visibility = View.GONE
                    binding.errorRecommendationDetail.errorMessage.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.tryAgainButton.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.errorMessage.text =
                        it.exception.localizedMessage
                    binding.errorDetail.tryAgainButton.setOnClickListener {
                        detailViewModel.getRecommendationMovies(showId)
                    }
                }
            }
        }

        detailViewModel.similarTVShows.observe(this) {
            when (it) {
                is Result.Success -> {
                    similarTVAdapter.submitList(it.data?.results)
                    binding.errorRecommendationDetail.root.visibility = View.GONE

                    if (it.data?.results.isNullOrEmpty()) {
                        binding.similarContainer.visibility = View.GONE
                    }
                }

                is Result.Loading -> {
                    binding.errorSimilarDetail.retryLoading.visibility = View.GONE
                    binding.errorSimilarDetail.errorMessage.visibility = View.GONE
                    binding.errorSimilarDetail.tryAgainButton.visibility = View.GONE
                    binding.errorSimilarDetail.retryLoading.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.errorSimilarDetail.root.visibility = View.VISIBLE
                    binding.errorSimilarDetail.retryLoading.visibility = View.GONE
                    binding.errorSimilarDetail.errorMessage.visibility = View.VISIBLE
                    binding.errorSimilarDetail.tryAgainButton.visibility = View.VISIBLE
                    binding.errorSimilarDetail.errorMessage.text = it.exception.localizedMessage
                    binding.errorSimilarDetail.tryAgainButton.setOnClickListener {
                        detailViewModel.getRecommendationMovies(showId)
                    }
                }
            }
        }
    }

    private fun observeAccountState() {
        detailViewModel.mediaState.observe(this) {
            if (it is Result.Success && it.data != null) {
                binding.favouriteFabDetail.setImageResource(
                    if (it.data.favorite) R.drawable.ic_favourite_active else R.drawable.ic_favourite_disable
                )
                favoriteState = (it.data.favorite).not()
            }
        }

        detailViewModel.favouriteResult.observe(this) {
            if (it is Result.Success && it.data?.statusCode != null) {
                binding.favouriteFabDetail.setImageResource(
                    if (it.data.statusCode == 13) R.drawable.ic_favourite_disable else R.drawable.ic_favourite_active
                )
                favoriteState = it.data.statusCode == 13
            }
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