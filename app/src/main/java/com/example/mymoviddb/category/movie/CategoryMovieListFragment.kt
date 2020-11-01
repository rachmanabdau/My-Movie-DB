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
import com.example.mymoviddb.adapters.MovieListAdapter
import com.example.mymoviddb.databinding.FragmentCategoryMovieListBinding
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryMovieListFragment : Fragment() {

    private lateinit var binding: FragmentCategoryMovieListBinding

    private val arguments by navArgs<CategoryMovieListFragmentArgs>()

    private val showViewModels by viewModels<CategoryMovieListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryMovieListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        Util.setupToolbar(requireActivity(), binding.movieToolbar.toolbar, findNavController())
        setUpToolbar(arguments.title)

        val adapter = MovieListAdapter { showViewModels.retry() }
        binding.showRv.adapter = adapter
        binding.lifecycleOwner = this
        var firstInitialize = true

        showViewModels.result.observe(viewLifecycleOwner, {
            adapter.setState(it)

            if (it is Result.Error && firstInitialize) {
                val message = it.exception.localizedMessage ?: "Unknown error has occured"
                binding.errorLayout.errorMessage.text = message
                binding.errorLayout.root.visibility = View.VISIBLE
                binding.loadingBar.visibility = View.GONE
            } else if (it is Result.Success) {
                firstInitialize = false
                binding.errorLayout.root.visibility = View.GONE
                binding.loadingBar.visibility = View.GONE
            } else if (it is Result.Loading && firstInitialize) {
                binding.loadingBar.visibility = View.VISIBLE
            }
        })

        showViewModels.movieList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        binding.errorLayout.tryAgainButton.setOnClickListener {
            showViewModels.retry()
        }

        return binding.root
    }

    private fun setUpToolbar(@StringRes subtitle: Int) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(subtitle)
    }

}