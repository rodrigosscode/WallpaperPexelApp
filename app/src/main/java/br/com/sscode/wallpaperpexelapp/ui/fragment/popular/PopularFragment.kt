package br.com.sscode.wallpaperpexelapp.ui.fragment.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import br.com.sscode.core.model.PhotoDomain
import br.com.sscode.wallpaperpexelapp.databinding.FragmentPopularBinding
import br.com.sscode.wallpaperpexelapp.ui.fragment.adapter.photoadapter.PhotoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularFragment : Fragment() {

    private lateinit var binding: FragmentPopularBinding
    private lateinit var photoAdapter: PhotoAdapter

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
    }

    private fun initAdapter() {
        photoAdapter = PhotoAdapter(::detail)
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        with(binding.recyclerView) {
            scrollToPosition(0)
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = photoAdapter
        }
    }

    private fun detail(photoDomain: PhotoDomain) {

    }
}