package com.example.pixabaytt.app.fragment.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pixabaytt.R
import com.example.pixabaytt.app.fragment.categories.CategoriesFragment.Companion.CATEGORY
import com.example.pixabaytt.app.fragment.categories.SimpleItemDecorator
import com.example.pixabaytt.app.fragment.images.adapter.ImagesAdapter
import com.example.pixabaytt.app.utils.dpToPx
import com.example.pixabaytt.app.utils.onEndOfListReached
import com.example.pixabaytt.databinding.FragmentImagesBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ImagesFragment : Fragment() {
    private lateinit var binding: FragmentImagesBinding

    companion object {
        const val IMAGE_ID = "IMAGE_ID"
    }

    private val viewModel by viewModel<ImagesViewModel> {
        parametersOf(arguments?.getString(CATEGORY))
    }
    private val imagesAdapter = ImagesAdapter {
        val bundle = Bundle()
        bundle.putInt(IMAGE_ID, it.id)
        findNavController().navigate(R.id.action_categoriesFragment_to_detailImageFragment, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.title= null
        binding = FragmentImagesBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            error.observe(viewLifecycleOwner) { errorMessage ->
                showError(errorMessage)
            }
            loading.observe(viewLifecycleOwner) { isLoading ->
                showLoading(isLoading)
            }
            notFound.observe(viewLifecycleOwner) { isNotFound ->
                showIfFounded(isNotFound)
            }
            isEmpty.observe(viewLifecycleOwner) { empty ->
                showPageLoad(empty)
            }
        }

        binding.recycler.apply {
            adapter = imagesAdapter
            val margin = requireContext().dpToPx(6)
            addItemDecoration(
                SimpleItemDecorator(
                    margin, margin,
                    margin, 0
                )
            )
            onEndOfListReached {
                viewModel.getNewPage()
            }
        }

        viewModel.apply {
            images.onEach {
                imagesAdapter.items = it.images
            }.launchIn(viewLifecycleOwner.lifecycleScope)
            error.observe(viewLifecycleOwner, ::showError)
        }
    }

    private fun showError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoading(isLoading: Boolean) = with(binding) {
        progressBarImages.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showIfFounded(notFound: String) {
        Snackbar.make(binding.root, notFound, Snackbar.LENGTH_LONG).show()
    }

    private fun showPageLoad(empty: String) {
        Snackbar.make(binding.root, empty, Snackbar.LENGTH_LONG).show()
    }
}