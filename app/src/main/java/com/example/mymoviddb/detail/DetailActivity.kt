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
import com.example.mymoviddb.databinding.ActivityDetailBinding
import com.example.mymoviddb.home.PreloadLinearLayout
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.LoginState
import com.example.mymoviddb.utils.PreferenceUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var recommendationAdapter: MoviesAdapter

    private lateinit var similarAdapter: MoviesAdapter

    private val detailViewModel by viewModels<DetailViewModel>()

    private val args by navArgs<DetailActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        setupToolbar()
        showHideActionButton()
        loadData()
        observeViewModel(args.showId)
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
        binding.fabContainer.visibility =
            if (state == LoginState.AS_GUEST.ordinal) View.GONE else View.VISIBLE
    }

    private fun initAdapter(showId: Long, loadId: Int) {
        if (loadId == DETAIL_MOVIE) {

            recommendationAdapter = MoviesAdapter({
                detailViewModel.getRecommendationMovies(showId)
            }, {
                setIntentDetail(it, loadId)
            }, false)

            binding.recommendtaionDetailRv.adapter = recommendationAdapter
            binding.recommendtaionDetailRv.layoutManager =
                PreloadLinearLayout(this, RecyclerView.HORIZONTAL, false)

            similarAdapter = MoviesAdapter({
                detailViewModel.getSimilarMovies(showId)
            }, {
                setIntentDetail(it, loadId)
            }, false)

            binding.similarDetailRv.adapter = similarAdapter
            binding.similarDetailRv.layoutManager =
                PreloadLinearLayout(this, RecyclerView.HORIZONTAL, false)
        }
    }

    private fun loadData() {
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

        if (loadId == DETAIL_MOVIE) {
            detailViewModel.getMovieDetail(showId)
            detailViewModel.getRecommendationMovies(showId)
            detailViewModel.getSimilarMovies(showId)
            binding.recommendationLabelDetail.text =
                getString(R.string.recommendation_detail_label, getString(R.string.movies_label))
            binding.similarLabelDetail.text =
                getString(R.string.similar_detail_label, getString(R.string.movies_label))
        } else {
            detailViewModel.getTVDetail(showId)
            binding.recommendationLabelDetail.text =
                getString(R.string.recommendation_detail_label, getString(R.string.tv_shows_label))
            binding.similarLabelDetail.text =
                getString(R.string.similar_detail_label, getString(R.string.tv_shows_label))
        }
    }

    private fun setIntentDetail(showId: Long, detailKey: Int) {
        intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DETAIL_KEY, detailKey)
        intent.putExtra(SHOW_ID_KEY, showId)
        startActivity(intent)
    }

    private fun observeViewModel(showId: Long) {
        // this code make marquee on text view works
        binding.detailToolbar.titleCustom.isSelected = true
        // observer for movie detail
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
                    similarAdapter.submitList(it.data?.results)
                }

                is Result.Loading -> {
                    binding.errorRecommendationDetail.root.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.retryLoading.visibility = View.GONE
                    binding.errorRecommendationDetail.errorMessage.visibility = View.GONE
                    binding.errorRecommendationDetail.tryAgainButton.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.errorRecommendationDetail.root.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.retryLoading.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.errorMessage.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.tryAgainButton.visibility = View.VISIBLE
                    binding.errorDetail.tryAgainButton.setOnClickListener {
                        detailViewModel.getRecommendationMovies(showId)
                    }
                }
            }
        }

        detailViewModel.recommendationMovies.observe(this) {
            when (it) {
                is Result.Success -> {
                    recommendationAdapter.submitList(it.data?.results)
                }

                is Result.Loading -> {
                    binding.errorRecommendationDetail.root.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.retryLoading.visibility = View.GONE
                    binding.errorRecommendationDetail.errorMessage.visibility = View.GONE
                    binding.errorRecommendationDetail.tryAgainButton.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.errorRecommendationDetail.root.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.retryLoading.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.errorMessage.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.tryAgainButton.visibility = View.VISIBLE
                    binding.errorDetail.tryAgainButton.setOnClickListener {
                        detailViewModel.getRecommendationMovies(showId)
                    }
                }
            }
        }

        detailViewModel.similarMovies.observe(this) {
            when (it) {
                is Result.Success -> {
                    similarAdapter.submitList(it.data?.results)
                }

                is Result.Loading -> {
                    binding.errorRecommendationDetail.root.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.retryLoading.visibility = View.GONE
                    binding.errorRecommendationDetail.errorMessage.visibility = View.GONE
                    binding.errorRecommendationDetail.tryAgainButton.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.errorRecommendationDetail.root.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.retryLoading.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.errorMessage.visibility = View.VISIBLE
                    binding.errorRecommendationDetail.tryAgainButton.visibility = View.VISIBLE
                    binding.errorDetail.tryAgainButton.setOnClickListener {
                        detailViewModel.getRecommendationMovies(showId)
                    }
                }
            }
        }

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
    }

    private fun setupToolbar() {
        // my_child_toolbar is defined in the layout file
        setSupportActionBar(binding.detailToolbar.toolbar)
        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}