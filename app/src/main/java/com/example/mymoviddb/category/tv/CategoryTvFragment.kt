package com.example.mymoviddb.category.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mymoviddb.adapters.PlaceHolderAdapter
import com.example.mymoviddb.category.movie.StateAdapter
import com.example.mymoviddb.databinding.FragmentCategoryTvBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryTvFragment : Fragment() {

    private lateinit var binding: FragmentCategoryTvBinding

    private val arguments by navArgs<CategoryTvFragmentArgs>()

    private val categoryTvViewmodel by viewModels<CategoryTVViewModel>()

    private lateinit var adapter: TVListAdapterV3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCategoryTvBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setUpToolbar(arguments.title)
        setupAdapter()
        binding.lifecycleOwner = this

        categoryTvViewmodel.getMovieData(TVDataSourceV3.POPULAR_TV_ID)
        categoryTvViewmodel.tvPageData.observe(viewLifecycleOwner, {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(it)
            }
        })
        return binding.root
    }

    private fun setUpToolbar(@StringRes subtitle: Int) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(subtitle)
    }

    private fun setupAdapter() {
        val placeholderAdapter = PlaceHolderAdapter()
        binding.shimmerPlaceholderCategoryTv.shimmerPlaceholder.adapter = placeholderAdapter
        binding.shimmerPlaceholderCategoryTv.shimmerPlaceholder.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        adapter = TVListAdapterV3 {
        }.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                loadStateFlow.collectLatest { loadState ->
                    // show shimmer place holder when in loading state
                    binding.shimmerPlaceholderCategoryTv.root.isVisible =
                        loadState.refresh is LoadState.Loading
                    // show error message and try agian button when in error state
                    binding.errorLayout.root.isVisible = loadState.refresh is LoadState.Error
                    binding.errorLayout.tryAgainButton.setOnClickListener {
                        retry()
                    }
                }
            }
        }

        binding.showRv.adapter = adapter.withLoadStateHeaderAndFooter(
            header = StateAdapter(adapter::retry), footer = StateAdapter(adapter::retry)
        )
    }

}