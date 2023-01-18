package com.example.restcountries.app.fragment.countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.restcountries.R
import com.example.restcountries.app.data.api.model.CountryModel
import com.example.restcountries.app.fragment.countries.adapter.CountriesAdapter
import com.example.restcountries.databinding.FragmentCountriesBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class CountriesFragment : Fragment() {
    private lateinit var binding: FragmentCountriesBinding

    private val viewModel by viewModel<CountriesViewModel>()

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
            countries.observe(viewLifecycleOwner) { countriesList ->
                showCountriesList(countriesList)
            }
            getCountries()
        }
        binding.button.setOnClickListener { viewModel.getCountries() }

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
        binding.countriesRecycler.adapter = CountriesAdapter(
            dataSet = countries
        ) {
            val bundle = Bundle()
            bundle.putParcelable("CountryModel", it)
            findNavController().navigate(R.id.action_countriesFragment_to_countryDetailFragment, bundle)
        }
    }
}