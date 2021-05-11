package com.example.mymoviddb.detail

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.R
import com.example.mymoviddb.adapters.PreviewShowAdapter
import com.example.mymoviddb.databinding.ActivityDetailBinding
import com.example.mymoviddb.home.PreloadLinearLayout
import com.example.mymoviddb.model.MovieDetail
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.ShowResult
import com.example.mymoviddb.model.TVDetail
import com.example.mymoviddb.utils.EventObserver
import com.example.mymoviddb.utils.LoginState
import com.example.mymoviddb.utils.UserPreference
import com.example.mymoviddb.utils.Util.disableViewDuringAnimation
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var recommendationShowAdapter: PreviewShowAdapter

    private lateinit var similarShowsAdapter: PreviewShowAdapter

    @Inject
    lateinit var userPreference: UserPreference

    private val detailViewModel by viewModels<DetailViewModel>()

    private var firstinitialize = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.lifecycleOwner = this
        binding.detailViewModel = detailViewModel

        val sessionId = userPreference.readUserSession()
        val showItem = intent.getParcelableExtra<ShowResult>(DETAIL_KEY)
        showItem?.apply {
            detailViewModel.getShowDetail(showItem, sessionId)
        }

        // Show snack bar message from add/remove favourite (either succeed or failed)
        // Show snack bar message from add to/remove from watch list  (either succeed or failed)
        detailViewModel.showSnackbarMessage.observe(this, EventObserver {
            Snackbar.make(binding.root, getString(it), Snackbar.LENGTH_SHORT).show()
        })

        detailViewModel.isFavourited.observe(this) {
            val colorTint =
                if (it) ContextCompat.getColor(this, R.color.colorFavouriteActive)
                else ContextCompat.getColor(this, R.color.colorBackgroound)

            // Do not play animation at first intialize, just set the color filter without animation
            if (!firstinitialize) {
                setAnimationRotation(
                    binding.favouriteBtnDetail, colorTint
                )
            } else {
                binding.favouriteBtnDetail.setColorFilter(colorTint)
            }
        }

        setupToolbar()
        showItem?.apply {
            setupFAB(this)
            loadData(this)
        }

    }

    companion object {
        const val DETAIL_KEY = "com.example.mymoviddb.detail.DetailActivity.DETAIL_KEY"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupFAB(showItem: ShowResult) {
        val state = userPreference.getAuthState()
        val sessionId = userPreference.readUserSession()
        val userId = userPreference.readAccountId()
        binding.showFAB = state != LoginState.AS_GUEST.stateId

        // Button click listener for add to favourite
        binding.favouriteBtnDetail.setOnClickListener {
            firstinitialize = false
            detailViewModel.markAsFavorite(userId, sessionId, showItem)
        }

        binding.addToWatchlistBtnDetail.setOnClickListener {
            firstinitialize = false
            detailViewModel.addToWatchList(userId, sessionId, showItem)
        }
    }

    private fun setAnimationRotation(
        imageView: ImageView,
        @ColorInt colorChange: Int
    ) {
        val rotator = ObjectAnimator.ofFloat(imageView, View.ROTATION_Y, -720f, 0f)
        rotator.disableViewDuringAnimation(imageView)

        val colorizerActive =
            ObjectAnimator.ofArgb(imageView, "colorFilter", colorChange)
        colorizerActive.repeatCount = 0
        colorizerActive.disableViewDuringAnimation(imageView)

        val set = AnimatorSet()
        set.playTogether(rotator, colorizerActive)
        set.duration = 1000L

        set.start()
    }

    private fun loadData(showItem: ShowResult) {

        initAdapter(showItem)
        observeShowsDetail(showItem)

        binding.recommendationLabelDetail.text =
            getString(R.string.recommendation_detail_label, getString(R.string.movies_label))
        binding.similarLabelDetail.text =
            getString(R.string.similar_detail_label, getString(R.string.movies_label))

    }

    private fun initAdapter(showItem: ShowResult) {
        recommendationShowAdapter = PreviewShowAdapter({
            detailViewModel.getRecommendationMovies(showItem)
        }, {
            setIntentDetail(it)
        }, false)

        binding.recommendtaionDetailRv.adapter = recommendationShowAdapter
        // layout manager for recommendation movies
        binding.recommendtaionDetailRv.layoutManager =
            PreloadLinearLayout(this, RecyclerView.HORIZONTAL, false)

        // Adapter for similar movies
        similarShowsAdapter = PreviewShowAdapter({
            detailViewModel.getSimilarMovies(showItem)
        }, {
            setIntentDetail(it)
        }, false)

        binding.similarDetailRv.adapter = similarShowsAdapter
        // layout manager for similar movies
        binding.similarDetailRv.layoutManager =
            PreloadLinearLayout(this, RecyclerView.HORIZONTAL, false)
    }

    // Navigation for recommendation and similar shows item to detail activity
    private fun setIntentDetail(showItem: ShowResult) {
        intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DETAIL_KEY, showItem)
        startActivity(intent)
    }

    private fun observeShowsDetail(showItem: ShowResult) {
        detailViewModel.showDetail.observe(this) {
            detailViewModel.determineDetailResult(it)
            if (it is Result.Success) {
                it.data?.apply {

                    binding.detailData = this
                    binding.detailToolbar.titleCustom.text = title
                    binding.detailToolbar.titleCustom.visibility = View.VISIBLE
                    binding.ratingDetail.text =
                        getString(R.string.rate_detail, (voteAverage * 10).toInt())
                    binding.genreDetail.text = genres.let {
                        val genreList = mutableListOf<String>()
                        it.forEach { genreList.add(it.name) }
                        genreList.joinToString(", ")
                    }

                    binding.releaseDateDetail.text = if (this is MovieDetail) {
                        getString(R.string.release_date_detail, this.releaseDate)
                    } else {
                        getString(R.string.first_air_date_detail, (this as TVDetail).firstAirDate)
                    }
                }
                detailViewModel.getRecommendationMovies(showItem)
                detailViewModel.getSimilarMovies(showItem)
            } else if (it is Result.Error) {
                binding.errorDetail.errorMessage.text = it.exception.localizedMessage
                binding.errorDetail.tryAgainButton.setOnClickListener {
                    val sessionId = userPreference.readUserSession()
                    detailViewModel.getShowDetail(showItem, sessionId)
                }
            }
        }

        detailViewModel.recommendationShows.observe(this) {
            when (it) {
                is Result.Success -> {
                    recommendationShowAdapter.submitList(it.data?.results)

                    if (it.data?.results.isNullOrEmpty()) {
                        binding.recommendationContainer.visibility = View.GONE
                    }
                }

                is Result.Error -> {
                    binding.errorRecommendationDetail.tryAgainButton.setOnClickListener {
                        detailViewModel.getRecommendationMovies(showItem)
                    }
                }

                else -> {/* Do nothing set else just to avoid linter */
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

        detailViewModel.similarShows.observe(this) {
            when (it) {
                is Result.Success -> {
                    similarShowsAdapter.submitList(it.data?.results)

                    if (it.data?.results.isNullOrEmpty()) {
                        binding.similarContainer.visibility = View.GONE
                    }
                }

                is Result.Error -> {
                    binding.errorSimilarDetail.errorMessage.text = it.exception.localizedMessage
                    binding.errorSimilarDetail.tryAgainButton.setOnClickListener {
                        detailViewModel.getSimilarMovies(showItem)
                    }
                }

                else -> {/* Do nothing set else just to avoid linter */
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

    private fun setupToolbar() {
        // my_child_toolbar is defined in the layout file
        setSupportActionBar(binding.detailToolbar.toolbar)
        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // this code make marquee on text view works
        binding.detailToolbar.titleCustom.isSelected = true
    }
}