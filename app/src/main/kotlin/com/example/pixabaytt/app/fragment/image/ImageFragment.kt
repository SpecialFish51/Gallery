package com.example.pixabaytt.app.fragment.image

import android.app.AlertDialog
import android.app.WallpaperManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.pixabaytt.app.data.domain.ImagesInfoModel
import com.example.pixabaytt.app.fragment.images.ImagesFragment.Companion.IMAGE_ID
import com.example.pixabaytt.databinding.FragmentImageBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.net.URL

class ImageFragment : Fragment() {
    private lateinit var binding: FragmentImageBinding

    private val viewModel by viewModel<ImageViewModel> {
        parametersOf(arguments?.getInt(IMAGE_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            image.onEach {
                setImage(it)
                setListener(it)
            }.launchIn(viewModelScope)
            error.observe(viewLifecycleOwner, ::showError)
        }
        (requireActivity() as AppCompatActivity).supportActionBar?.title= null
    }

    private fun setListener(imageInfo: ImagesInfoModel?) = with(binding) {
        imageInfo?.let { imageInfo ->
            button.setOnClickListener {
                showAlertDialog(imageInfo)
            }
        }
    }

    private fun setImage(imageInfo: ImagesInfoModel?) {
        imageInfo?.let {
            Picasso.get().load(imageInfo.largeImageURL)
                .noPlaceholder()
                .into(binding.image)
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showAlertDialog(imageInfo: ImagesInfoModel?) {
        AlertDialog.Builder(requireContext()).setPositiveButton(
            "Оk"
        ) { _, _ -> setWallPaper(imageInfo) }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setTitle("Are you sure?")
            .setMessage("This will set image on wallpaper")
            .show()
    }

    private fun setWallPaper(imageInfo: ImagesInfoModel?) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val inputStream = URL(imageInfo?.largeImageURL).openStream()
            WallpaperManager.getInstance(requireContext()).setStream(inputStream)
        }
    }

}