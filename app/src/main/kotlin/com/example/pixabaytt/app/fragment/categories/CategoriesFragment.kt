package com.example.pixabaytt.app.fragment.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pixabaytt.R
import com.example.pixabaytt.app.fragment.categories.adapter.CategoriesAdapter
import com.example.pixabaytt.app.utils.dpToPx
import com.example.pixabaytt.databinding.FragmentCategoriesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding

    private val viewModel by viewModel<CategoriesViewModel>()

    companion object {
        const val CATEGORY = "CATEGORY"
    }

    private val categoriesAdapter = CategoriesAdapter {
        val bundle = Bundle()
        bundle.putString(CATEGORY, it.name)
        findNavController().navigate(R.id.action_categoriesFragment_to_imagesFragment, bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.countriesRecycler.apply {
            adapter = categoriesAdapter
            val margin = requireContext().dpToPx(8)
            addItemDecoration(
                SimpleItemDecorator(
                    0, margin, 0, margin
                )
            )

        }
        categoriesAdapter.items = viewModel.setDataToAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }
}
