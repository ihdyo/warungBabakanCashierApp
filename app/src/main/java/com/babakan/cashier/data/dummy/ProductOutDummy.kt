package com.babakan.cashier.data.dummy

import com.babakan.cashier.presentation.owner.model.ProductOutModel
import com.google.firebase.Timestamp

val dummyProductOutList = listOf(
    ProductOutModel(
        id = "p1",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        price = 7000.0,
        quantity = 3
    ),
    ProductOutModel(
        id = "p2",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        price = 12000.0,
        quantity = 5
    ),
    ProductOutModel(
        id = "p3",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        price = 3000.0,
        quantity = 2
    ),
    ProductOutModel(
        id = "p4",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        price = 7000.0,
        quantity = 6
    ),
    ProductOutModel(
        id = "p5",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        price = 15000.0,
        quantity = 4
    ),
    ProductOutModel(
        id = "p6",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        price = 15000.0,
        quantity = 1
    ),
)
