package com.babakan.cashier.utils.formatter

import com.google.firebase.Timestamp
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object Formatter {

    private val locale = Locale("in", "ID")

    fun currency(number: Double): String {
        val numberFormatter = NumberFormat.getCurrencyInstance(locale)
        numberFormatter.currency = Currency.getInstance("IDR")
        numberFormatter.maximumFractionDigits = 2
        numberFormatter.minimumFractionDigits = 0
        return numberFormatter.format(number)
    }

    fun cleanDecimal(number: Double): String {
        return if (number % 1.0 == 0.0) {
            number.toInt().toString()
        } else {
            number.toString()
        }
    }

    fun date(timestamp: Timestamp): String {
        val pattern = "dd MMM yyyy"
        val dateFormat = SimpleDateFormat(pattern, locale)
        val millis = timestamp.toDate().time
        val date = Date(millis)
        return dateFormat.format(date)
    }

    fun firstName(name: String): String {
        return name.split(" ")[0]
    }
}