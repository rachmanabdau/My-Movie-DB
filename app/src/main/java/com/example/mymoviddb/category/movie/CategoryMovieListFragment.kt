package com.example.mymoviddb.category.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mymoviddb.adapters.MovieListAdapter
import com.example.mymoviddb.adapters.PlaceHolderAdapter
import com.example.mymoviddb.databinding.FragmentCategoryMovieListBinding
import com.example.mymoviddb.detail.DetailActivity
import com.example.mymoviddb.model.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryMovieListFragment : Fragment() {

    private lateinit var binding: FragmentCategoryMovieListBinding

    private val arguments by navArgs<CategoryMovieListFragmentArgs>()

    private val showViewModels by viewModels<CategoryMovieListViewModel>()

    private lateinit var adapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryMovieListBinding.inflate(inflater, container, false)
        setUpToolbar(arguments.title)
        binding.lifecycleOwner = this
        var firstInitialize = true
        setupAdapter()

        // observe status while loading item per page
        showViewModels.result.observe(viewLifecycleOwner, {
            adapter.setState(it)

            if (it is Result.Error && firstInitialize) {
                val message = it.exception.localizedMessage ?: "Unknown error has occured"
                binding.errorLayout.errorMessage.text = message
                binding.errorLayout.root.visibility = View.VISIBLE
                binding.shimmerPlaceholderCategoryMovie.root.visibility = View.GONE
            } else if (it is Result.Success) {
                firstInitialize = false
                binding.errorLayout.root.visibility = View.GONE
                binding.shimmerPlaceholderCategoryMovie.root.visibility = View.GONE
            } else if (it is Result.Loading && firstInitialize) {
                binding.shimmerPlaceholderCategoryMovie.root.visibility = View.VISIBLE
            }
        })

        // Add Item with pagination
        showViewModels.movieList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        // set try again button listener
        binding.errorLayout.tryAgainButton.setOnClickListener {
            showViewModels.retry()
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

        adapter = MovieListAdapter({ showViewModels.retry() },
            {
                findNavController().navigate(
                    CategoryMovieListFragmentDirections.actionCategoryMovieListFragmentToDetailActivity(
                        DetailActivity.DETAIL_MOVIE, it
                    )
                )
            })
        binding.showRv.adapter = adapter
    }
}