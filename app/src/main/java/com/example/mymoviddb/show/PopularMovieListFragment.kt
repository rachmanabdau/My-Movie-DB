package com.example.mymoviddb.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mymoviddb.adapters.MovieListAdapter
import com.example.mymoviddb.databinding.FragmentPopularMovieListBinding
import com.example.mymoviddb.datasource.remote.PopularMovieDataSourceFactory
import com.example.mymoviddb.datasource.remote.RemoteServerAccess
import com.example.mymoviddb.model.Result

class PopularMovieListFragment : Fragment() {

    private lateinit var binding: FragmentPopularMovieListBinding

    private val showViewModels by viewModels<PopularMovieViewModel> {
        val movieDataSourceFactory =
            PopularMovieDataSourceFactory(RemoteServerAccess(), lifecycleScope)
        PopularMovieViewModel.Factory(movieDataSourceFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularMovieListBinding.inflate(inflater, container, false)

        val adapter = MovieListAdapter { showViewModels.retry() }
        binding.showRv.adapter = adapter

        binding.lifecycleOwner = this

        showViewModels.result.observe(viewLifecycleOwner, {
            adapter.setState(it)

            if (it is Result.Error) {
                val message = it.exception.localizedMessage ?: "Unknown error has occured"
                binding.errorLayout.errorMessage.text = message
            } else if (it is Result.Success) {
                binding.errorLayout.root.visibility =
                    if (it.data?.results.isNullOrEmpty()) View.VISIBLE else View.GONE
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


}