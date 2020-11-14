package com.flaviu.coronaplannerapp.travel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.flaviu.coronaplannerapp.model.Country
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class TravelViewModel(context: Context) : ViewModel() {
    val text = MutableLiveData<String>()
    val countries: LiveData<List<Country>> = Transformations.map(text){ matchingName ->
        val data = mutableListOf<Country>()
        Country.getCountries(context).filter{ country ->
            Regex(matchingName.toLowerCase(Locale.ROOT)).
            containsMatchIn(country.country.toLowerCase(Locale.ROOT))
        }
    }

    var navigateToCountryInfo = MutableLiveData<String>()

    fun onCardClicked(country: String) {
        navigateToCountryInfo.value = country
    }

    fun onCardClickFinished() {
        navigateToCountryInfo.value = null
    }
}