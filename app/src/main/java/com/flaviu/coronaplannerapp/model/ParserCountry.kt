package com.flaviu.coronaplannerapp.model

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

class ParserCountry(private val name: String, private val context: Context) {
    private fun readFile(fileName: String): List<String> {
        val file = BufferedReader(InputStreamReader(context.assets.open(fileName)))
        return file.useLines {it.toList()}
    }

    fun getUrl(): String? {
        val countries = readFile("Ministry.txt")
        var url: String? = null
        for (i in countries) {
            if (i.startsWith(this.name)) {
                url = i.substringAfter(" - ")
            }
        }
        return url
    }

    fun getWorldometersUrl(): String? {
        var englishName: String? = null
        val countries = readFile("Worldometer.txt")
        for (i in countries) {
            if (i.startsWith(this.name)) {
                englishName = i.substringAfter(" - ")
            }
        }
        return englishName
    }
}