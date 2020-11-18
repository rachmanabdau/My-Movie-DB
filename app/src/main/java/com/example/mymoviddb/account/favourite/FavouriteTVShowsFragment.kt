package com.example.mymoviddb.account.favourite

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
import com.example.mymoviddb.databinding.FragmentFavouriteTvShowsBinding
import com.example.mymoviddb.detail.DetailActivity
import com.example.mymoviddb.model.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteTVShowsFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteTvShowsBinding

    private val favouriteViewModel by viewModels<AccountShowViewModel>()

    private var firstInitialize = true

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
                findNavController().navigate(
                    FavouriteTVShowsFragmentDirections.actionFavouriteTVShowsFragmentToDetailActivity(
                        DetailActivity.DETAIL_TV,
                        it
                    )
                )
            }
        )
        binding.favouriteRv.adapter = adapter
        binding.favouriteSwipeRefresh.setOnRefreshListener {
            adapter.submitList(null)
            adapter.notifyDataSetChanged()
            favouriteViewModel.getFavouriteTVShows()
        }

        favouriteViewModel.getFavouriteTVShows()
        favouriteViewModel.accountShowList.observe(viewLifecycleOwner) {
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
                binding.favouriteErrorLayout.root.visibility =
                    if (it.data?.results.isNullOrEmpty() && firstInitialize) View.VISIBLE else View.GONE
                binding.favouriteErrorLayout.errorMessage.text =
                    getString(R.string.empty_favourite_tv_show)
                binding.favouriteErrorLayout.tryAgainButton.visibility = View.GONE
                binding.favouriteSwipeRefresh.isRefreshing = false
                firstInitialize = false
            } else if (it is Result.Loading && firstInitialize) {
                binding.favouriteSwipeRefresh.isRefreshing = true
            }
        })
        return binding.root
    }

}