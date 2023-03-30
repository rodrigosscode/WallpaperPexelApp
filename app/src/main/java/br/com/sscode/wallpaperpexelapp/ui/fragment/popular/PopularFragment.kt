package br.com.sscode.wallpaperpexelapp.ui.fragment.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import br.com.sscode.core.model.PhotoDomain
import br.com.sscode.wallpaperpexelapp.databinding.FragmentPopularBinding
import br.com.sscode.wallpaperpexelapp.ui.fragment.adapter.photoadapter.PhotoAdapter
import br.com.sscode.wallpaperpexelapp.ui.fragment.main.MainFragmentDirections
import br.com.sscode.wallpaperpexelapp.ui.fragment.popular.viewmodel.PopularViewModel
import br.com.sscode.wallpaperpexelapp.util.animationCancel
import br.com.sscode.wallpaperpexelapp.util.pulseAnimation
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularFragment : Fragment() {

    private lateinit var binding: FragmentPopularBinding
    private lateinit var photoAdapter: PhotoAdapter
    private val popularViewModel: PopularViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeLoadingState()
        observerFavoriteUiState()
        fetchWallpapers()
    }

    private fun initAdapter() {
        photoAdapter = PhotoAdapter(::detail, ::insertData)
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        with(binding.recyclerView) {
            scrollToPosition(0)
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = photoAdapter
        }
    }

    private fun fetchWallpapers() = lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            popularViewModel.popularWallpapers().collect { pagingData ->
                photoAdapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadingState() = lifecycleScope.launch {
        photoAdapter.loadStateFlow.collectLatest { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    binding.imagePulseAnimation.visibility = View.VISIBLE
                    binding.imagePulseAnimation.pulseAnimation()
                }
                is LoadState.NotLoading -> {
                    binding.imagePulseAnimation.visibility = View.GONE
                    binding.imagePulseAnimation.animationCancel()
                }
                is LoadState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun observerFavoriteUiState() {
        popularViewModel.favoriteUiState.observe(viewLifecycleOwner) { favoriteUiState ->
            when (favoriteUiState) {
                PopularViewModel.FavoriteUiState.Loading -> 1
                is PopularViewModel.FavoriteUiState.FavoritePhoto -> {
                    if (favoriteUiState.saved) {
                        favoriteItemMessage("item salvo")
                    } else {
                        favoriteItemMessage(
                            "erro ao salvar"
                        )
                    }
                }
            }
        }
    }

    private fun favoriteItemMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setAnimationMode(ANIMATION_MODE_SLIDE).show()
    }

    private fun detail(photoDomain: PhotoDomain) {
        val data = arrayOf(photoDomain.srcDomain?.original, photoDomain.description)
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDownloadFragment(
                data
            )
        )
    }

    private fun insertData(photoDomain: PhotoDomain) {
        popularViewModel.favoritePhoto(photoDomain)
    }
}