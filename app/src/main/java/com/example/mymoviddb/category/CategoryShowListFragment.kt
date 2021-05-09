package com.example.mymoviddb.category

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mymoviddb.adapters.CategoryShowAdapter
import com.example.mymoviddb.adapters.PlaceHolderAdapter
import com.example.mymoviddb.category.movie.StateAdapter
import com.example.mymoviddb.databinding.FragmentCategoryShowListBinding
import com.example.mymoviddb.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryShowListFragment : Fragment() {

    private lateinit var binding: FragmentCategoryShowListBinding

    private val arguments by navArgs<CategoryShowListFragmentArgs>()

    private val showViewModels by viewModels<CategoryShowListViewModel>()

    private lateinit var adapter: CategoryShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryShowListBinding.inflate(inflater, container, false)
        setUpToolbar(arguments.title)
        binding.lifecycleOwner = this
        setupAdapter()

        showViewModels.getShowCategory(arguments.categoryId)
        showViewModels.showPageData.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }

        return binding.root
    }

    private fun setUpToolbar(@StringRes subtitle: Int) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(subtitle)
    }

    private fun setupAdapter() {
        val placeholderAdapter = PlaceHolderAdapter()
        binding.shimmerPlaceholderCategoryMovie.shimmerPlaceholder.adapter = placeholderAdapter
        binding.shimmerPlaceholderCategoryMovie.shimmerPlaceholder.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        adapter = CategoryShowAdapter {
            findNavController().navigate(
                CategoryShowListFragmentDirections.actionCategoryMovieListFragmentToDetailActivity(
                    DetailActivity.DETAIL_MOVIE, it.id
                )
            )
        }.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                loadStateFlow.collectLatest { loadState ->
                    // show shimmer place holder when in loading state
                    binding.shimmerPlaceholderCategoryMovie.root.isVisible =
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