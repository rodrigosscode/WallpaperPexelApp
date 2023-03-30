package br.com.sscode.wallpaperpexelapp.util

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import br.com.sscode.core.model.PhotoDomain
import br.com.sscode.wallpaperpexelapp.databinding.FragmentDialogCustomBinding
import br.com.sscode.wallpaperpexelapp.ui.extension.loadBlurredImageWithPlaceholder
import com.google.android.material.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CustomDialog(
    private val photoDomain: PhotoDomain,
    private val clickListener: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = FragmentDialogCustomBinding.inflate(layoutInflater)
        binding.btnNo.setOnClickListener {
            dismiss()
        }
        binding.btnYes.setOnClickListener {
            clickListener.invoke()
            dismiss()
        }
        binding.image.loadBlurredImageWithPlaceholder(
            photoDomain.srcDomain?.small,
            photoDomain.avgColor
        )
        return MaterialAlertDialogBuilder(
            requireContext(),
            R.style.MaterialAlertDialog_Material3
        )
        .setCancelable(false)
        .setView(binding.root)
        .create()
        .apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}