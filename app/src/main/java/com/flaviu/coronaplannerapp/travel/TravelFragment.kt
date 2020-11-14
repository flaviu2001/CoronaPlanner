package com.flaviu.coronaplannerapp.travel

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.flaviu.coronaplannerapp.databinding.TravelFragmentBinding

class TravelFragment : Fragment() {
    private lateinit var binding: TravelFragmentBinding
    private lateinit var travelViewModel: TravelViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = TravelFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val travelViewModelFactory = TravelViewModelFactory(requireContext())
        travelViewModel = ViewModelProvider(this, travelViewModelFactory).get(TravelViewModel::class.java)
        binding.viewModel = travelViewModel
        val adapter = CountryListAdapter(CountryClickListener{
            travelViewModel.onCardClicked(it)
        })
        travelViewModel.navigateToCountryInfo.observe(viewLifecycleOwner){
            it?.let{
                this.findNavController().navigate(TravelFragmentDirections.actionNavTravelToNavInfo(it))
                travelViewModel.onCardClickFinished()
            }
        }
        binding.countryList.adapter = adapter
        travelViewModel.countries.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        travelViewModel.text.value = binding.searchEdit.text.toString()
        binding.searchEdit.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                travelViewModel.text.value = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        return binding.root
    }
}