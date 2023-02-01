package com.example.restcountries.app.fragment.countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.restcountries.R
import com.example.restcountries.app.data.domain.CountryModel
import com.example.restcountries.app.fragment.countries.adapter.CountriesAdapter
import com.example.restcountries.app.utils.SwipeHelper
import com.example.restcountries.databinding.FragmentCountriesBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel


class CountriesFragment : Fragment() {
    private lateinit var binding: FragmentCountriesBinding

    private val viewModel by viewModel<CountriesViewModel>()

    private val adapter = CountriesAdapter {
        val bundle = Bundle()
        bundle.putParcelable("CountryModel", it)
        findNavController().navigate(R.id.action_countriesFragment_to_countryDetailFragment, bundle)
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
            countries.onEach { countriesList ->
                showCountriesList(countriesList)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            getCountries()
        }
        binding.button.setOnClickListener { viewModel.getCountries() }


        SwipeHelper { position ->
            viewModel.onItemDelete(position)
        }.attachToRecyclerView(binding.countriesRecycler)

        binding.countriesRecycler.adapter = adapter

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountriesBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun showError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoading(isLoading: Boolean) = with(binding) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showIfFounded(notFound: String) {
        Snackbar.make(binding.root, notFound, Snackbar.LENGTH_LONG).show()
    }

    private fun showPageLoad(empty: String) {
        Snackbar.make(binding.root, empty, Snackbar.LENGTH_LONG).show()
    }

    private fun showCountriesList(countries: List<CountryModel>) {
        adapter.items = countries
    }
}