package com.example.mymoviddb.account.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mymoviddb.R
import com.example.mymoviddb.account.AccountShowViewModel
import com.example.mymoviddb.adapters.FavouriteAdapter
import com.example.mymoviddb.databinding.FragmentWatchListMovieBinding
import com.example.mymoviddb.detail.DetailActivity
import com.example.mymoviddb.model.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchListMovieFragment : Fragment() {

    private lateinit var binding: FragmentWatchListMovieBinding

    private val watchListViewmodel by viewModels<AccountShowViewModel>()

    private var firstInitialize = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWatchListMovieBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.watchlistErrorLayout.tryAgainButton.visibility = View.GONE

        val adapter = FavouriteAdapter(
            {
                watchListViewmodel.retryLoadFavourite()
            },
            {
                findNavController().navigate(
                    WatchListMovieFragmentDirections.actionWatchListMovieFragmentToDetailActivity(
                        DetailActivity.DETAIL_MOVIE,
                        it
                    )
                )
            }
        )
        binding.watchlistRv.adapter = adapter
        binding.watchlistSwipeRefresh.setOnRefreshListener {
            adapter.submitList(null)
            adapter.notifyDataSetChanged()
            watchListViewmodel.getWatchListMovies()
        }

        watchListViewmodel.getWatchListMovies()
        watchListViewmodel.accountShowList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        watchListViewmodel.resultFavourite.observe(viewLifecycleOwner, {
            adapter.setState(it)

            if (it is Result.Error && firstInitialize) {
                val message = it.exception.localizedMessage ?: "Unknown error has occured"
                binding.watchlistErrorLayout.errorMessage.text =
                    getString(R.string.swipe_refresh_hint, message)
                binding.watchlistErrorLayout.root.visibility = View.VISIBLE
                binding.watchlistSwipeRefresh.isRefreshing = false
            } else if (it is Result.Success) {
                binding.watchlistErrorLayout.root.visibility =
                    if (it.data?.results.isNullOrEmpty() && firstInitialize) View.VISIBLE else View.GONE
                binding.watchlistErrorLayout.errorMessage.text =
                    getString(R.string.empty_watchlist_movie)
                binding.watchlistErrorLayout.tryAgainButton.visibility = View.GONE
                binding.watchlistSwipeRefresh.isRefreshing = false
                firstInitialize = false
            } else if (it is Result.Loading && firstInitialize) {
                binding.watchlistSwipeRefresh.isRefreshing = true
            }
        })


        return binding.root
    }

}