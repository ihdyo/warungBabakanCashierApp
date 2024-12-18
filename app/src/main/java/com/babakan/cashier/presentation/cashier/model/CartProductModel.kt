package com.babakan.cashier.presentation.cashier.model

import java.sql.Timestamp

data class CartProductModel(
    val documentId: String,
    val createdAt: Timestamp,
    var updateAt: Timestamp,
    var price: Double,
    var quantity: Int
)

val dummyCartList = listOf(
    CartProductModel(
        documentId = "1",
        createdAt = Timestamp(System.currentTimeMillis()),
        updateAt = Timestamp(System.currentTimeMillis()),
        price = 10000.0,
        quantity = 1
    ),
    CartProductModel(
        documentId = "2",
        createdAt = Timestamp(System.currentTimeMillis()),
        updateAt = Timestamp(System.currentTimeMillis()),
        price = 10000.0,
        quantity = 1
    ),
    CartProductModel(
        documentId = "3",
        createdAt = Timestamp(System.currentTimeMillis()),
        updateAt = Timestamp(System.currentTimeMillis()),
        price = 10000.0,
        quantity = 1
    )
)
