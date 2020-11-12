package com.example.mymoviddb.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mymoviddb.R
import com.example.mymoviddb.adapters.FavouriteAdapter
import com.example.mymoviddb.databinding.FragmentFavouriteTvShowsBinding
import com.example.mymoviddb.model.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteTVShowsFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteTvShowsBinding

    private val favouriteViewModel by viewModels<FavouriteShowViewModel>()

    private var firstInitialize = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteTvShowsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.favouriteErrorLayout.tryAgainButton.visibility = View.GONE

        val adapter = FavouriteAdapter(
            {
                favouriteViewModel.retryLoadFavourite()
            },
            {
                // navigate to detail activity
            }
        )
        binding.favouriteRv.adapter = adapter
        binding.favouriteSwipeRefresh.setOnRefreshListener {
            adapter.submitList(null)
            adapter.notifyDataSetChanged()
            favouriteViewModel.getFavouriteTVShows()
        }

        favouriteViewModel.getFavouriteTVShows()
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
                binding.favouriteErrorLayout.root.visibility = View.GONE
                binding.favouriteSwipeRefresh.isRefreshing = false
            } else if (it is Result.Loading && firstInitialize) {
                binding.favouriteSwipeRefresh.isRefreshing = true
            }
        })
        return binding.root
    }

}