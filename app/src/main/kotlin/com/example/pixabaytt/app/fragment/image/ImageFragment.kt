package com.example.pixabaytt.app.fragment.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pixabaytt.app.data.domain.ImagesModel
import com.example.pixabaytt.R
import com.example.pixabaytt.databinding.FragmentCountryDetailBinding
import com.squareup.picasso.Picasso

class CountryDetailFragment : Fragment() {
    private lateinit var binding: FragmentCountryDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val countryModel = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable("CountryModel", ImagesModel::class.java)
        } else {
            arguments?.getParcelable("CountryModel")
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.title = countryModel?.name

        countryModel?.let { country ->
            binding.apply {
                lCountryName.text = country.name
                lCapital.text = country.capital.ifBlank { getString(R.string.no_data) }
                lCurrency.text = country.currencies.let { list ->
                    list.joinToString { currency ->
                        currency.name + (", " + currency.code) + (", " + currency.symbol)
                    }
                }
                lTimeZone.text = country.timeZones.joinToString { timezone ->
                    timezone
                }
                Picasso.get().load(countryModel.flags.png)
                    .noPlaceholder()
                    .into(lFlag)
            }
        }
    }
}