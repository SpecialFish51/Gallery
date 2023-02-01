package com.example.restcountries.app.fragment.countries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.restcountries.R
import com.example.restcountries.app.data.domain.CountryModel
import com.example.restcountries.app.utils.FlagSize.FLAG_HEIGHT
import com.example.restcountries.app.utils.FlagSize.FLAG_WIDTH
import com.example.restcountries.databinding.ItemCountryBinding
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates



class CountriesAdapter(
    private val onCountryClicked: (country: CountryModel) -> Unit
) : RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    var items: List<CountryModel> by Delegates.observable(emptyList()) { prop, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.position == n.position }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        private val binding: ItemCountryBinding,
        private val onCountryClicked: (country: CountryModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(countryModel: CountryModel) {
            binding.countryName.text = countryModel.name

            Picasso.get().load(countryModel.flags.png)
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
        viewHolder.bind(items[position])
    }
}

fun <T> RecyclerView.Adapter<*>.autoNotify(oldList: List<T>, newList: List<T>, compare: (T, T) -> Boolean) {
    val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return compare(oldList[oldItemPosition], newList[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size
    })

    diff.dispatchUpdatesTo(this)
}







