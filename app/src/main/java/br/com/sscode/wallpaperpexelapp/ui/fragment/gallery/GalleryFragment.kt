package br.com.sscode.wallpaperpexelapp.ui.fragment.gallery

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.*
import br.com.sscode.core.model.PhotoDomain
import br.com.sscode.wallpaperpexelapp.databinding.FragmentGalleryBinding
import br.com.sscode.wallpaperpexelapp.framework.workmanager.WallpaperWork
import br.com.sscode.wallpaperpexelapp.ui.fragment.adapter.galleryadapter.GalleryAdapter
import br.com.sscode.wallpaperpexelapp.ui.fragment.gallery.viewmodel.GalleryViewModel
import br.com.sscode.wallpaperpexelapp.util.CustomDialog
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val WORK_NAME = "WALLPAPER_WORK"

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding
    private lateinit var galleryAdapter: GalleryAdapter

    @Inject
    lateinit var workManager: WorkManager

    private val galleryViewModel: GalleryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureBackButton()
        initAdapter()
        getAllPhotos()
        startWorker(workManager)
    }

    private fun initAdapter() {
        galleryAdapter = GalleryAdapter(::detail, ::delete)
        val gridLayout = GridLayoutManager(requireContext(), 3)
        with(binding.galleryRv) {
            layoutManager = gridLayout
            setHasFixedSize(true)
            adapter = galleryAdapter
        }
    }

    private fun delete(photoDomain: PhotoDomain) {
        val dialog = CustomDialog(photoDomain) {
            galleryViewModel.delete(photoDomain)
        }

        dialog.show(childFragmentManager, "Delete Dialog")
    }

    private fun detail(photoDomain: PhotoDomain) {
        val data = arrayOf(photoDomain.srcDomain?.original, photoDomain.description)
        findNavController().navigate(
            GalleryFragmentDirections.actionGalleryFragmentToDownloadFragment(
                data
            )
        )
    }

    private fun getAllPhotos() {
        galleryViewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is GalleryViewModel.UiState.ShowGallery -> {
                    galleryAdapter.submitList(uiState.photos)
                }
                GalleryViewModel.UiState.EmptyGallery -> {
                    galleryAdapter.submitList(emptyList())
                }
                GalleryViewModel.UiState.Error -> {
                    errorMessage()
                }
            }
        }
    }

    private fun configureBackButton() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun errorMessage() {
        Snackbar.make(binding.root, "Ops, um erro ocorreu", Snackbar.LENGTH_SHORT)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show()
    }

    @SuppressLint("InvalidPeriodicWorkRequestInterval")
    private fun startWorker(workManager: WorkManager) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val wallpaperWorker =
            PeriodicWorkRequest.Builder(WallpaperWork::class.java, 1, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            wallpaperWorker
        )
    }

    private fun cancelWorker(workManager: WorkManager) {
        workManager.cancelUniqueWork(WORK_NAME)
    }
}