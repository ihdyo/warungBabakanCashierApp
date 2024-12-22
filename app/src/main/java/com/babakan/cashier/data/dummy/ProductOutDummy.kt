package com.babakan.cashier.data.dummy

import com.babakan.cashier.presentation.owner.model.ProductOutModel
import com.google.firebase.Timestamp

val dummyProductOutList = listOf(
    ProductOutModel(
        productId = "p1",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        price = 7000.0,
        quantity = 3
    ),
    ProductOutModel(
        productId = "p2",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        price = 12000.0,
        quantity = 5
    ),
    ProductOutModel(
        productId = "p3",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        price = 3000.0,
        quantity = 2
    ),
    ProductOutModel(
        productId = "p4",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        price = 7000.0,
        quantity = 6
    ),
    ProductOutModel(
        productId = "p5",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        price = 15000.0,
        quantity = 4
    ),
    ProductOutModel(
        productId = "p6",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        price = 15000.0,
        quantity = 1
    ),
)
