package br.com.sscode.wallpaperpexelapp.ui.fragment.adapter.galleryadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.sscode.core.model.PhotoDomain
import br.com.sscode.wallpaperpexelapp.ui.fragment.adapter.photoadapter.PhotoViewHolder

class GalleryAdapter(
    private val clickCallback: (photoDomain: PhotoDomain) -> Unit,
    private val longClickCallback: (photoDomain: PhotoDomain) -> Unit
) : ListAdapter<PhotoDomain, PhotoViewHolder>(differCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder.create(parent, clickCallback, longClickCallback)

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<PhotoDomain>() {
            override fun areItemsTheSame(oldItem: PhotoDomain, newItem: PhotoDomain): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: PhotoDomain, newItem: PhotoDomain): Boolean {
                return oldItem == newItem
            }
        }
    }
}