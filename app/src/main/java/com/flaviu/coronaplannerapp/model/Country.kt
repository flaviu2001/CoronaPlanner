package com.flaviu.coronaplannerapp.model

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

class Country(val country: String, val context: Context){
    companion object{
        fun getCountries(context: Context): List<Country> {
            val file = BufferedReader(InputStreamReader(context.assets.open("SimpleNames.txt")))
            val toReturn = file.readLines().sorted().map{ Country(it, context) }
            file.close()
            return toReturn
        }
    }

    fun getTravelInfo(): String {
        return Parser(country, context).parseRules()
    }

    fun getCasesInfo(): String {
        return Parser(country, context).parseWorld()
    }
}