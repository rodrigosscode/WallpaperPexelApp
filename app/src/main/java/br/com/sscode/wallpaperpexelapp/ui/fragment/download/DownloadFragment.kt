package br.com.sscode.wallpaperpexelapp.ui.fragment.download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.sscode.wallpaperpexelapp.R
import br.com.sscode.wallpaperpexelapp.databinding.FragmentDownloadBinding
import com.bumptech.glide.Glide

class DownloadFragment : Fragment() {

    private lateinit var binding: FragmentDownloadBinding
    private val args: DownloadFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDownloadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadImage(args.image[0])
        backButton()
        bottomSheet()
    }

    private fun loadImage(url: String) {
        Glide.with(requireContext())
            .load(url)
            .centerCrop()
            .fallback(R.drawable.baseline_broken)
            .into(binding.downloadImage)
    }

    private fun backButton() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun bottomSheet() {
        val bottomSheetDownload = BottomSheetDownload(args.image[0], args.image[1])
        binding.downloadButton.setOnClickListener {
            bottomSheetDownload.show(requireActivity().supportFragmentManager, "BOTTOM_SHEET")
        }
    }
}