package com.babakan.cashier.utils.formatter

import com.google.firebase.Timestamp
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object Formatter {

    private val locale = Locale("in", "ID")

    fun currency(number: Double): String {
        val numberFormatter = NumberFormat.getCurrencyInstance(locale)
        numberFormatter.maximumFractionDigits = 0
        return numberFormatter.format(number)
    }

    fun date(timestamp: Timestamp): String {
        val pattern = "dd MMM yyyy"
        val dateFormat = SimpleDateFormat(pattern, locale)
        val millis = timestamp.toDate().time
        val date = Date(millis)
        return dateFormat.format(date)
    }

}