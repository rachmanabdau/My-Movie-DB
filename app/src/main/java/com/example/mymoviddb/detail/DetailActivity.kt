package com.example.mymoviddb.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.example.mymoviddb.R
import com.example.mymoviddb.databinding.ActivityDetailBinding
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.LoginState
import com.example.mymoviddb.utils.PreferenceUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

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

    private fun showHideActionButton() {
        val state = PreferenceUtil.getAuthState(this)
        binding.fabContainer.visibility =
            if (state == LoginState.AS_GUEST.ordinal) View.GONE else View.VISIBLE
    }

    private fun loadData() {
        val loadId = if (args.loadDetailId != 0) {
            args.loadDetailId
        } else {
            intent.getLongExtra(DETAIL_KEY, 0)
        }

        val showId = if (args.showId != 0L) {
            args.showId
        } else {
            intent.getLongExtra(SHOW_ID_KEY, 0L)
        }

        if (loadId == DETAIL_MOVIE) {
            detailViewModel.getMovieDetail(showId)
        } else {
            detailViewModel.getTVDetail(showId)
        }
    }

    private fun observeViewModel(showId: Long) {
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