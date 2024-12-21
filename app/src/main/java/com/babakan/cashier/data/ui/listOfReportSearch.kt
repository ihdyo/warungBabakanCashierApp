package com.babakan.cashier.data.ui

import android.content.Context
import com.babakan.cashier.R

fun listOfReportSearch(context: Context): List<String> {
    return listOf(
        context.getString(R.string.transactionNumber),
        context.getString(R.string.user),
        context.getString(R.string.date),
    )
}