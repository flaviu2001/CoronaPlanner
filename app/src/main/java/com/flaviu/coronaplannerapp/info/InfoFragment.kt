package com.flaviu.coronaplannerapp.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.flaviu.coronaplannerapp.R
import com.flaviu.coronaplannerapp.databinding.InfoFragmentBinding
import com.flaviu.coronaplannerapp.model.Country
import kotlinx.coroutines.*
import java.lang.Exception

class InfoFragment : Fragment() {
    private lateinit var binding: InfoFragmentBinding
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.info_fragment, container, false)
        binding.lifecycleOwner = this
        val arguments = InfoFragmentArgs.fromBundle(requireArguments())
        val country = Country(arguments.country, requireContext())
        binding.country.text = country.country
        binding.casesInfo = "Se incarca.."
        binding.travelInfo = "Se incarca.."
        uiScope.launch {
            withContext(Dispatchers.IO) {
                lateinit var casesInfo: String
                lateinit var travelInfo: String
                try {
                    casesInfo = country.getCasesInfo()
                    travelInfo = country.getTravelInfo()
                }catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                    return@withContext
                }
                withContext(Dispatchers.Main) {
                    binding.casesInfo = casesInfo
                    binding.travelInfo = travelInfo
                }
            }
        }
        return binding.root
    }
}