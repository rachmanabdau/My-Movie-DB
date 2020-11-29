package com.example.mymoviddb.category.tv

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
import com.example.mymoviddb.adapters.PlaceHolderAdapter
import com.example.mymoviddb.adapters.TVListAdapter
import com.example.mymoviddb.databinding.FragmentCategoryTvBinding
import com.example.mymoviddb.detail.DetailActivity
import com.example.mymoviddb.model.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryTvFragment : Fragment() {

    private lateinit var binding: FragmentCategoryTvBinding

    private val arguments by navArgs<CategoryTvFragmentArgs>()

    private val categoryTvViewmodel by viewModels<CategoryTVViewModel>()

    private lateinit var adapter: TVListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCategoryTvBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setUpToolbar(arguments.title)
        setupAdapter()
        binding.lifecycleOwner = this
        var firstInitialize = true

        categoryTvViewmodel.result.observe(viewLifecycleOwner, {
            adapter.setState(it)

            if (it is Result.Error && firstInitialize) {
                val message = it.exception.localizedMessage ?: "Unknown error has occured"
                binding.errorLayout.errorMessage.text = message
                binding.errorLayout.root.visibility = View.VISIBLE
                binding.shimmerPlaceholderCategoryTv.root.visibility = View.GONE
            } else if (it is Result.Success) {
                firstInitialize = false
                binding.errorLayout.root.visibility = View.GONE
                binding.shimmerPlaceholderCategoryTv.root.visibility = View.GONE
            } else if (it is Result.Loading && firstInitialize) {
                binding.shimmerPlaceholderCategoryTv.root.visibility = View.VISIBLE
            }
        })

        categoryTvViewmodel.tvList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        binding.errorLayout.tryAgainButton.setOnClickListener {
            categoryTvViewmodel.retry()
        }
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

        adapter = TVListAdapter({ categoryTvViewmodel.retry() },
            {
                findNavController().navigate(
                    CategoryTvFragmentDirections.actionCategoryTvFragmentToDetailActivity(
                        DetailActivity.DETAIL_TV, it
                    )
                )
            })
        binding.showRv.adapter = adapter
    }

}