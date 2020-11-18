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
import com.example.mymoviddb.databinding.FragmentWatchListTvBinding
import com.example.mymoviddb.detail.DetailActivity
import com.example.mymoviddb.model.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchListTVFragment : Fragment() {

    private lateinit var binding: FragmentWatchListTvBinding

    private val watchListTVViewModel by viewModels<AccountShowViewModel>()

    private var firstInitialize = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchListTvBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.watchlistTvErrorLayout.tryAgainButton.visibility = View.GONE

        val adapter = FavouriteAdapter(
            {
                watchListTVViewModel.retryLoadFavourite()
            },
            {
                findNavController().navigate(
                    WatchListTVFragmentDirections.actionWatchListTVFragmentToDetailActivity(
                        DetailActivity.DETAIL_TV,
                        it
                    )
                )
            }
        )
        binding.watchlistTvRv.adapter = adapter
        binding.watchlistTvSwipeRefresh.setOnRefreshListener {
            adapter.submitList(null)
            adapter.notifyDataSetChanged()
            watchListTVViewModel.getWatchListTVShows()
        }

        watchListTVViewModel.getWatchListTVShows()
        watchListTVViewModel.accountShowList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        watchListTVViewModel.resultFavourite.observe(viewLifecycleOwner, {
            adapter.setState(it)

            if (it is Result.Error && firstInitialize) {
                val message = it.exception.localizedMessage ?: "Unknown error has occured"
                binding.watchlistTvErrorLayout.errorMessage.text =
                    getString(R.string.swipe_refresh_hint, message)
                binding.watchlistTvErrorLayout.root.visibility = View.VISIBLE
                binding.watchlistTvSwipeRefresh.isRefreshing = false
            } else if (it is Result.Success) {
                binding.watchlistTvErrorLayout.root.visibility =
                    if (it.data?.results.isNullOrEmpty() && firstInitialize) View.VISIBLE else View.GONE
                binding.watchlistTvErrorLayout.errorMessage.text =
                    getString(R.string.empty_watchlist_tv_show)
                binding.watchlistTvErrorLayout.tryAgainButton.visibility = View.GONE
                binding.watchlistTvSwipeRefresh.isRefreshing = false
                firstInitialize = false
            } else if (it is Result.Loading && firstInitialize) {
                binding.watchlistTvSwipeRefresh.isRefreshing = true
            }
        })
        return binding.root
    }

}