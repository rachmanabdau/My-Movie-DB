package com.example.mymoviddb.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.bumptech.glide.Glide
import com.example.mymoviddb.R
import com.example.mymoviddb.adapters.PlaceHolderAdapter
import com.example.mymoviddb.adapters.PreviewShowAdapter
import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.PreloadLinearLayout
import com.example.mymoviddb.core.ShowCategoryIndex
import com.example.mymoviddb.core.model.ShowResult
import com.example.mymoviddb.core.model.UserDetail
import com.example.mymoviddb.core.utils.EventObserver
import com.example.mymoviddb.core.utils.preference.LoginState
import com.example.mymoviddb.core.utils.preference.Preference
import com.example.mymoviddb.databinding.FragmentHomeBinding
import com.example.mymoviddb.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var popularMoviesAdapter: PreviewShowAdapter

    @Inject
    lateinit var nowPlayingMoviesAdapter: PreviewShowAdapter

    @Inject
    lateinit var popularTvShowsAdapter: PreviewShowAdapter

    @Inject
    lateinit var onAirTvShowsAdapter: PreviewShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        launchLoginFragment()
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setupToolbar(findNavController())
        binding.lifecycleOwner = this
        homeViewModel.populateData()
        binding.homeViewModel = homeViewModel

        initializeAdapter()
        setClickListener()
        setUpBackPressed()
        observeUserAvatar()
        observeLogoutResult()

        homeViewModel.snackbarMessage.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) ||
                super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (homeViewModel.userIsLogin()) homeViewModel.getUserDetail()
    }

    private fun launchLoginFragment() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData(
            loginStateKey,
            homeViewModel.getAuthStete()
        )?.observe(viewLifecycleOwner) {
            if (it == LoginState.NOT_LOGIN.stateId) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                )
            }
        }
    }

    private fun initializeAdapter() {
        initiatePlaceHolderAdapter()
        setupPopularMovieList()
        setupNowPlayingMovieList()
        setupPopularTvShowList()
        setupOnAirTvShowList()
    }

    private fun initiatePlaceHolderAdapter() {
        binding.popularMoviesPlaceholder.shimmerPlaceholder.adapter = PlaceHolderAdapter()
        binding.nowPlayingMoviesPlaceholder.shimmerPlaceholder.adapter = PlaceHolderAdapter()
        binding.popularTvPlaceholder.shimmerPlaceholder.adapter = PlaceHolderAdapter()
        binding.onAirTvPlaceholder.shimmerPlaceholder.adapter = PlaceHolderAdapter()
    }

    private fun setupPopularMovieList() {
        binding.popularMovieRv.adapter = popularMoviesAdapter
            .showLoadMore(true)
            .setLoadmoreClick { navigateToCategoryPopularMovies() }
            .setNavigationToDetail { navigateToDetailShow(it) }

        binding.popularMovieRv.layoutManager = PreloadLinearLayout(requireContext())
    }

    private fun navigateToCategoryPopularMovies() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryGraph(
                R.string.popular_movie_list_contentDesc, ShowCategoryIndex.POPULAR_MOVIES
            )
        )
    }

    private fun setupNowPlayingMovieList() {
        binding.nowPlayingMovieRv.adapter = nowPlayingMoviesAdapter
            .showLoadMore(true)
            .setLoadmoreClick { navigateToCategoryNowPlayingMovies() }
            .setNavigationToDetail { navigateToDetailShow(it) }

        binding.nowPlayingMovieRv.layoutManager = PreloadLinearLayout(requireContext())
    }

    private fun navigateToCategoryNowPlayingMovies() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryGraph(
                R.string.now_playing_movie_list_contentDesc,
                ShowCategoryIndex.NOW_PLAYING_MOVIES
            )
        )
    }

    private fun setupPopularTvShowList() {
        binding.popularTvRv.adapter = popularTvShowsAdapter
            .showLoadMore(true)
            .setLoadmoreClick { navigateToCategoryPopularTvShows() }
            .setNavigationToDetail { navigateToDetailShow(it) }

        binding.popularTvRv.layoutManager = PreloadLinearLayout(requireContext())
    }

    private fun navigateToCategoryPopularTvShows() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryGraph(
                R.string.popular_tv_show_list_contentDesc, ShowCategoryIndex.POPULAR_TV_SHOWS
            )
        )
    }

    private fun setupOnAirTvShowList() {
        binding.onAirPopularTvRv.adapter = onAirTvShowsAdapter
            .showLoadMore(true)
            .setLoadmoreClick { navigateToCategoryOnAirTvShows() }
            .setNavigationToDetail { navigateToDetailShow(it) }

        binding.onAirPopularTvRv.layoutManager = PreloadLinearLayout(requireContext())
    }

    private fun navigateToCategoryOnAirTvShows() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryGraph(
                R.string.now_airing_tv_show_list_contentDesc, ShowCategoryIndex.ON_AIR_TV_SHOWS
            )
        )
    }

    private fun navigateToDetailShow(showItem: ShowResult) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailGraph(
                showItem
            )
        )
    }

    private fun setClickListener() {
        setPopularMoviesReloadButton()
        setNowPlayingMoviesReloadButton()
        setPopularTvShowsReloadButton()
        setOnAirTvShowsReloadButton()
    }

    private fun setPopularMoviesReloadButton() {
        binding.errorPopularMoviesMessage.tryAgainButton.setOnClickListener {
            binding.errorPopularMoviesMessage.tryAgainButton.isEnabled = false
            homeViewModel.getPopularMovieList()
            binding.errorPopularMoviesMessage.tryAgainButton.isEnabled = true
        }
    }

    private fun setNowPlayingMoviesReloadButton() {
        binding.errorNowPlayingMoviesMessage.tryAgainButton.setOnClickListener {
            binding.errorNowPlayingMoviesMessage.tryAgainButton.isEnabled = false
            homeViewModel.getNowPlayingMovieList()
            binding.errorNowPlayingMoviesMessage.tryAgainButton.isEnabled = true
        }
    }

    private fun setPopularTvShowsReloadButton() {
        binding.errorPopularTvMessage.tryAgainButton.setOnClickListener {
            binding.errorPopularTvMessage.tryAgainButton.isEnabled = false
            homeViewModel.getPopularTVList()
            binding.errorPopularTvMessage.tryAgainButton.isEnabled = true
        }
    }

    private fun setOnAirTvShowsReloadButton() {
        binding.errorOnAirTvMessage.tryAgainButton.setOnClickListener {
            binding.errorOnAirTvMessage.tryAgainButton.isEnabled = false
            homeViewModel.getonAirTVList()
            binding.errorOnAirTvMessage.tryAgainButton.isEnabled = true
        }
    }

    private fun setUpBackPressed() {
        // navigate to
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            when {
                homeViewModel.userIsLoginAsGuest() -> {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                    )
                }

                binding.drawerLayout.isDrawerOpen(GravityCompat.START) ->
                    binding.drawerLayout.closeDrawer(GravityCompat.START)

                else -> requireActivity().finish()
            }

        }
    }

    private fun observeUserAvatar() {
        val headerItem = binding.navView.getHeaderView(0)
        val userAvatar =
            (headerItem.findViewById<CircleImageView>(R.id.user_avatar_header))
        val usernameHeader =
            (headerItem.findViewById<TextView>(R.id.username_header))

        homeViewModel.userDetail.observe(viewLifecycleOwner) {
            if (it is Result.Success && it.data != null) {
                it.data?.let { detail ->
                    usernameHeader.text = detail.username
                    loadUserAvatar(userAvatar, detail.avatar.tmdb, detail.username)
                }
            }
        }
    }

    private fun loadUserAvatar(
        userIMageView: ImageView,
        userTmdb: UserDetail.Avatar.Tmdb?,
        username: String
    ) {
        val imageUrl = if (userTmdb != null) {
            BuildConfig.LOAD_POSTER_BASE_URL + userTmdb.avatarPath
        } else {
            "https://eu.ui-avatars.com/api/?background=random&name=${username}"
        }

        Glide.with(this)
            .load(imageUrl)
            .into(userIMageView)
    }

    private fun observeLogoutResult() {
        homeViewModel.logoutResult.observe(viewLifecycleOwner) {
            if (it is Result.Success && it.data?.success == true) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    private fun setupToolbar(navController: NavController) {
        val isUserLogin = homeViewModel.userIsLogin()
        val activityContainer = (requireActivity() as AppCompatActivity)

        // set drawer lock mode
        val drawerLockedMode = if (isUserLogin) DrawerLayout.LOCK_MODE_UNLOCKED
        else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        binding.drawerLayout.setDrawerLockMode(drawerLockedMode)

        val appbarConfig =
            if (isUserLogin) AppBarConfiguration(navController.graph, binding.drawerLayout)
            else AppBarConfiguration(navController.graph)

        activityContainer.setSupportActionBar(binding.defaultToolbar.toolbar)
        NavigationUI.setupWithNavController(
            binding.defaultToolbar.toolbar,
            navController,
            appbarConfig
        )

        // listener click for logout menu. it has to be placed right after NavigationUI.setupWithNavController
        binding.navView.setNavigationItemSelectedListener {
            if (it.itemId == R.id.logout_drawer_menu) {
                launchLogoutConfirmation()
            }

            // to maintain navigation component still work
            NavigationUI.onNavDestinationSelected(it, navController)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }
    }

    private fun launchLogoutConfirmation() {
        val alertDialog: AlertDialog = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("Yes") { dialog, _ ->
                    homeViewModel.logout()
                    dialog.dismiss()
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            }
            builder.setTitle(getString(R.string.log_out))
                .setMessage(getString(R.string.log_out_confirmation))
            // Create the AlertDialog
            builder.create()
        }

        alertDialog.show()
    }

    companion object {
        const val loginStateKey = "com.example.mymoviddb.home.HomeFragment.loginStateKey"
    }
}