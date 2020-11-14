package com.flaviu.coronaplannerapp.model

import android.content.Context
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class Parser(countryName: String, context: Context) {

    var country = ParserCountry(countryName, context)

    fun parseRules(): String {
        val document: Document = Jsoup.connect(country.getUrl()).get()
        val elem: Elements = document.getElementsByClass("art")
        val toReturn = StringBuilder()
        for (x in elem.select("p,ul")) {
            if (x.`is`("ul")) {
                for (y in x.select("li"))
                    toReturn.append(y.text()).append("\n\n")
                continue
            }
            if (x.text().contains(Regex("[a-zA-Z0-9]")))
                toReturn.append(x.text()).append("\n\n")
        }
        return toReturn.toString()
    }

    private fun deathsOverCases(cases: Long, deaths: Long): Float {
        return deaths.toFloat()/cases*1000
    }

    private fun casesOverPopulation(population: Long, cases: Long): Float {
        return cases.toFloat()/population*1000
    }

    fun parseWorld(): String {
        val countryLink = country.getWorldometersUrl()!!
        val document: Document = Jsoup.connect(countryLink).get()
        val title: String = document.title()
        val elements = title.substringAfter(": ").substringBefore(" - ")
        val cases = elements.substringBefore(" and ").replace(" Cases", "")
        val deaths = elements.substringAfter(" and ").replace(" Deaths", "")
        var population = ""
        var populationLong = 0L
        var tests = ""
        val casesLong = cases.replace(",", "").toLong()
        val deathsLong = deaths.replace(",", "").toLong()
        val tableDocument: Document = Jsoup.connect("https://www.worldometers.info/coronavirus/#countries").get()
        val tableElem = tableDocument.getElementById("main_table_countries_today").select("tbody")
        for (tableEntry in tableElem) {
            val columns = tableEntry.select("tr")
            for (entryRow in columns) {
                val a = entryRow.selectFirst("a")
                if (a != null) {
                    val link = a.attr("href")
                    if (Regex(link).containsMatchIn(countryLink)) {
                        population = entryRow.select("td")[14].text()
                        populationLong = entryRow.select("td")[14].text().replace(",", "").toLong()
                        tests = entryRow.select("td")[12].text()
                    }
                }
            }
        }
        return "Populatie:  $population\nCazuri:        $cases\nDecese:      $deaths\nTeste:         $tests\n\n" +
                "Decese la mia de cazuri:\n    ${deathsOverCases(casesLong, deathsLong)}\n" +
                "Cazuri la mia de locuitori:\n    ${casesOverPopulation(populationLong, casesLong)}"
    }
}

