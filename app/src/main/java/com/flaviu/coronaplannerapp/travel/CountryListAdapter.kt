package com.flaviu.coronaplannerapp.travel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flaviu.coronaplannerapp.databinding.SimpleTextBinding
import com.flaviu.coronaplannerapp.model.Country

class CountryListAdapter(private val clickListener: CountryClickListener)
    : ListAdapter<Country, CountryViewHolder>(CardDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }
}

class CardDiffCallback :
    DiffUtil.ItemCallback<Country>() {
    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.country == newItem.country
    }

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.country == newItem.country
    }
}

class CountryViewHolder private constructor(private val binding: SimpleTextBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(clickListener: CountryClickListener, country: Country) {
        binding.clickListener = clickListener
        binding.country = country
    }

    companion object {
        fun from(parent: ViewGroup): CountryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SimpleTextBinding.inflate(layoutInflater, parent, false)
            return CountryViewHolder(binding)
        }
    }
}

class CountryClickListener(val clickListener: (country: String) -> Unit) {
    fun onClick(country: Country) = clickListener(country.country)
}
