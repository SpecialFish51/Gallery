package com.example.restcountries.app.fragment.countries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restcountries.R
import com.example.restcountries.app.data.api.model.CountryModel
import com.example.restcountries.app.utils.FlagSize.FLAG_HEIGHT
import com.example.restcountries.app.utils.FlagSize.FLAG_WIDTH
import com.example.restcountries.databinding.ItemCountryBinding
import com.squareup.picasso.Picasso

class CountriesAdapter(
    private val dataSet: List<CountryModel>,
    private val onCountryClicked: (country: CountryModel) -> Unit
) : RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {


    class ViewHolder(
        private val binding: ItemCountryBinding, private val onCountryClicked: (country: CountryModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(countryModel: CountryModel) {
            binding.countryName.text = countryModel.name

            Picasso.get().load(countryModel.flags?.png)
                .placeholder(R.drawable.ic_no_flag)
                .resize(FLAG_HEIGHT, FLAG_WIDTH)
                .into(binding.lFlag)
            binding.root.setOnClickListener {
                onCountryClicked.invoke(countryModel)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemCountryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(view, onCountryClicked)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

}