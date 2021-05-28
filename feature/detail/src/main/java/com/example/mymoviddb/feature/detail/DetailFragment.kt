package com.example.mymoviddb.feature.detail

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mymoviddb.adapters.PreviewShowAdapter
import com.example.mymoviddb.core.PreloadLinearLayout
import com.example.mymoviddb.core.model.MovieDetail
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.ShowResult
import com.example.mymoviddb.core.model.TVDetail
import com.example.mymoviddb.core.model.category.movie.MovieField
import com.example.mymoviddb.core.utils.EventObserver
import com.example.mymoviddb.core.utils.Util.disableViewDuringAnimation
import com.example.mymoviddb.core.utils.preference.LoginState
import com.example.mymoviddb.core.utils.preference.UserPreference
import com.example.mymoviddb.feature.detail.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    @Inject
    lateinit var recommendationShowAdapter: PreviewShowAdapter

    @Inject
    lateinit var similarShowsAdapter: PreviewShowAdapter

    @Inject
    lateinit var userPreference: UserPreference

    private val detailViewModel by viewModels<DetailViewModel>()

    private val showItem by lazy {
        DetailFragmentArgs.fromBundle(requireArguments()).showItem
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.detailViewModel = detailViewModel
        initLayout()
        loadData()

        // Show snack bar message from add/remove favourite (either succeed or failed)
        // Show snack bar message from add to/remove from watch list  (either succeed or failed)
        detailViewModel.showSnackbarMessage.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(binding.root, getString(it), Snackbar.LENGTH_SHORT).show()
        })

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initLayout() {
        val sessionId = userPreference.readUserSession()
        detailViewModel.getShowDetail(showItem, sessionId)
        setupFAB()

        detailViewModel.isFavourite.observe(viewLifecycleOwner) {
            val colorTint =
                if (it) ContextCompat.getColor(requireContext(), R.color.colorFavouriteActive)
                else ContextCompat.getColor(requireContext(), R.color.colorBackgroound)

            setAnimationRotation(
                binding.favouriteBtnDetail, colorTint
            )
        }
    }

    private fun setupFAB() {
        val state = userPreference.getAuthState()
        val sessionId = userPreference.readUserSession()
        val userId = userPreference.readAccountId()
        binding.showFAB = state != LoginState.AS_GUEST.stateId

        // Button click listener for add to favourite
        binding.favouriteBtnDetail.setOnClickListener {
            detailViewModel.markAsFavorite(userId, sessionId, showItem)
        }

        binding.addToWatchlistBtnDetail.setOnClickListener {
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

    private fun loadData() {
        initAdapter()
        observeShowsDetail()
    }

    private fun initAdapter() {
        setRecommendationLabel()
        setRecommendationadapter()
        setSimilarLabel()
        setSimilarAdapter()
    }

    private fun setRecommendationadapter() {
        // Adapter for recommendation shows
        binding.recommendtaionDetailRv.adapter = recommendationShowAdapter
            .showLoadMore(false)
            .setLoadmoreClick(null)
            .setNavigationToDetail { navigateToSelf(it) }

        // layout manager for recommendation shows
        binding.recommendtaionDetailRv.layoutManager = PreloadLinearLayout(requireContext())
    }

    private fun setRecommendationLabel() {
        binding.recommendationLabelDetail.text = if (showItem is MovieField) {
            getString(R.string.recommendation_detail_label, getString(R.string.movies_label))
        } else {
            getString(R.string.recommendation_detail_label, getString(R.string.tv_shows_label))
        }
    }

    private fun setSimilarAdapter() {
        // Adapter for similar shows
        binding.similarDetailRv.adapter = similarShowsAdapter
            .showLoadMore(false)
            .setLoadmoreClick(null)
            .setNavigationToDetail { navigateToSelf(it) }

        // layout manager for similar movies
        binding.similarDetailRv.layoutManager = PreloadLinearLayout(requireContext())
    }

    private fun setSimilarLabel() {
        binding.similarLabelDetail.text = if (showItem is MovieField) {
            getString(R.string.similar_detail_label, getString(R.string.movies_label))
        } else {
            getString(R.string.similar_detail_label, getString(R.string.tv_shows_label))
        }
    }

    private fun observeShowsDetail() {
        detailViewModel.showDetail.observe(viewLifecycleOwner) {
            detailViewModel.determineDetailResult(it)
            if (it is Result.Success) {
                it.data?.apply {
                    binding.detailData = this
                    setupToolbar(title)
                    binding.ratingDetail.text =
                        getString(R.string.rate_detail, (voteAverage * 10).toInt())
                    binding.genreDetail.text = genres.let { list ->
                        val genreList = mutableListOf<String>()
                        list.forEach { genre -> genreList.add(genre.name) }
                        genreList.joinToString(", ")
                    }

                    binding.releaseDateDetail.text = if (this is MovieDetail) {
                        getString(R.string.release_date_detail, this.releaseDate)
                    } else {
                        getString(R.string.first_air_date_detail, (this as TVDetail).firstAirDate)
                    }
                }
            } else if (it is Result.Error) {
                binding.errorDetail.errorMessage.text = it.exception.localizedMessage
                binding.errorDetail.tryAgainButton.setOnClickListener {
                    val sessionId = userPreference.readUserSession()
                    detailViewModel.getShowDetail(showItem, sessionId)
                }
            }
        }

        detailViewModel.recommendationShows.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    recommendationShowAdapter.submitList(it.data?.results)

                    if (it.data?.results.isNullOrEmpty()) {
                        binding.recommendationContainer.visibility = View.GONE
                    }
                }

                is Result.Error -> {
                    binding.errorRecommendationDetail.tryAgainButton.setOnClickListener {
                        detailViewModel.getRecommendationShows(showItem)
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

        detailViewModel.similarShows.observe(viewLifecycleOwner) {
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
                        detailViewModel.getSimilarShows(showItem)
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

    private fun navigateToSelf(showItem: ShowResult) {
        findNavController().navigate(
            DetailFragmentDirections.actionDetailFragmentSelf(showItem)
        )
    }

    private fun setupToolbar(showTitle: String?) {
        val activityContainer = requireActivity() as AppCompatActivity
        binding.detailToolbar.titleCustom.text = showTitle
        binding.detailToolbar.titleCustom.visibility = View.VISIBLE
        binding.detailToolbar.toolbar.setupWithNavController(findNavController())
        // my_child_toolbar is defined in the layout file
        //activityContainer.setSupportActionBar(binding.detailToolbar.toolbar)
        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        activityContainer.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // this code make marquee on text view works
        binding.detailToolbar.titleCustom.isSelected = true
    }


}