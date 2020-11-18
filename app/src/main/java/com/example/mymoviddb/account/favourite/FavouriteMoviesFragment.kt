package com.example.mymoviddb.account.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mymoviddb.R
import com.example.mymoviddb.adapters.FavouriteAdapter
import com.example.mymoviddb.databinding.FragmentFavouriteMoviesBinding
import com.example.mymoviddb.detail.DetailActivity
import com.example.mymoviddb.model.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteMoviesFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteMoviesBinding

    private val favouriteViewModel by viewModels<FavouriteShowViewModel>()

    private var firstInitialize = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteMoviesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.favouriteErrorLayout.tryAgainButton.visibility = View.GONE

        val adapter = FavouriteAdapter(
            {
                favouriteViewModel.retryLoadFavourite()
            },
            {
                findNavController().navigate(
                    FavouriteMoviesFragmentDirections.actionFavouriteMoviesFragmentToDetailActivity(
                        DetailActivity.DETAIL_MOVIE,
                        it
                    )
                )
            }
        )
        binding.favouriteRv.adapter = adapter
        binding.favouriteSwipeRefresh.setOnRefreshListener {
            adapter.submitList(null)
            adapter.notifyDataSetChanged()
            favouriteViewModel.getFavouriteMovies()
        }

        favouriteViewModel.getFavouriteMovies()
        favouriteViewModel.favouriteList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        favouriteViewModel.resultFavourite.observe(viewLifecycleOwner, {
            adapter.setState(it)

            if (it is Result.Error && firstInitialize) {
                val message = it.exception.localizedMessage ?: "Unknown error has occured"
                binding.favouriteErrorLayout.errorMessage.text =
                    getString(R.string.swipe_refresh_hint, message)
                binding.favouriteErrorLayout.root.visibility = View.VISIBLE
                binding.favouriteSwipeRefresh.isRefreshing = false
            } else if (it is Result.Success) {
                firstInitialize = false
                binding.favouriteErrorLayout.root.visibility =
                    if (it.data?.results.isNullOrEmpty() && firstInitialize) View.VISIBLE else View.GONE
                binding.favouriteErrorLayout.errorMessage.text =
                    getString(R.string.empty_favourite_movie)
                binding.favouriteErrorLayout.tryAgainButton.visibility = View.GONE
                binding.favouriteSwipeRefresh.isRefreshing = false
            } else if (it is Result.Loading && firstInitialize) {
                binding.favouriteSwipeRefresh.isRefreshing = true
            }
        })


        return binding.root
    }

}