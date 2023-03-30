package br.com.sscode.wallpaperpexelapp.ui.fragment.adapter.photoadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.sscode.core.model.PhotoDomain
import br.com.sscode.wallpaperpexelapp.databinding.ItemPhotoBinding
import br.com.sscode.wallpaperpexelapp.ui.extension.loadBlurredImageWithPlaceholder

class PhotoViewHolder(
    itemPhotoBinding: ItemPhotoBinding,
    private val clickCallback: (photo: PhotoDomain) -> Unit,
    private val longClickCallback: (photo: PhotoDomain) -> Unit
) : RecyclerView.ViewHolder(itemPhotoBinding.root) {

    private val image = itemPhotoBinding.image
    private val name = itemPhotoBinding.name

    fun bind(photo: PhotoDomain) {
//        Glide.with(itemView.context).load(photo.srcDomain.original)
//            .centerCrop()
//            .fallback(R.drawable.baseline_broken)
//            .into(image)

        image.loadBlurredImageWithPlaceholder(
            imageUrl = photo.srcDomain?.original,
            placeholderColor = photo.avgColor
        )

        name.text = photo.photographer

        itemView.setOnClickListener {
            clickCallback(photo)
        }

        itemView.setOnLongClickListener {
            longClickCallback(photo)
            return@setOnLongClickListener true
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            clickCallback: (photo: PhotoDomain) -> Unit,
            longClickCallback: (photoDomain: PhotoDomain) -> Unit
        ): PhotoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemPhotoBinding.inflate(inflater, parent, false)
            return PhotoViewHolder(itemBinding, clickCallback, longClickCallback)
        }
    }
}